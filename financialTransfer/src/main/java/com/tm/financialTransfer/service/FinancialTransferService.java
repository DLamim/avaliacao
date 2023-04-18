package com.tm.financialTransfer.service;

import com.tm.financialTransfer.dto.FinancialTransferDto;
import com.tm.financialTransfer.exception.ResourceNotFoundException;
import com.tm.financialTransfer.mapper.FinancialTransferMapper;
import com.tm.financialTransfer.model.BankAccount;
import com.tm.financialTransfer.model.FinancialTransfer;
import com.tm.financialTransfer.repository.BankAccountRepository;
import com.tm.financialTransfer.repository.FinancialTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FinancialTransferService {

    private Logger logger = Logger.getLogger(FinancialTransferService.class.getName());

    @Autowired
    FinancialTransferRepository financialTransferRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    FinancialTransferMapper financialTransferMapper;

    @Autowired
    CalculationService calculationService;

    public List<FinancialTransferDto> findAllFinancialTransfer() {

        logger.info("Finding all financial transfers!");

        return financialTransferMapper.listToDto(financialTransferRepository.findAll());
    }

    public FinancialTransferDto findFinancialTransferById(Long id) {
        String logMessage = String.format("Finding financial transfer of ID: %d", id);
        logger.info(logMessage);

        var entity = financialTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No results for this ID"));

        return financialTransferMapper.toDto(entity);
    }

    public FinancialTransferDto createFinancialTransfer(FinancialTransferDto financialTransferDto) throws AccountNotFoundException {
        String logMessage = String.format("Creating financial transfer from account %s to account %s.", financialTransferDto.getSenderAccount(), financialTransferDto.getBeneficiaryAccount());
        logger.info(logMessage);

        Optional<BankAccount> sender = Optional.ofNullable(bankAccountRepository.findByAccountNumber(financialTransferDto.getSenderAccount()));
        Optional<BankAccount> beneficiary = Optional.ofNullable(bankAccountRepository.findByAccountNumber(financialTransferDto.getBeneficiaryAccount()));

        if (sender.isEmpty()) {
            throw new AccountNotFoundException("Sender account not found.");
        }

        if (beneficiary.isEmpty()) {
            throw new AccountNotFoundException("Beneficiary account not found.");
        }

        FinancialTransfer financialTransfer = financialTransferMapper.toModel(financialTransferDto, sender.get(), beneficiary.get());

        financialTransfer = calculationService.calculateTax(financialTransfer);

        financialTransfer = financialTransferRepository.save(financialTransfer);

        return financialTransferMapper.toDto(financialTransfer);
    }
}
