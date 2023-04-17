package com.tm.financialTransfer.service;

import com.tm.financialTransfer.dto.FinancialTransferDto;
import com.tm.financialTransfer.exception.ResourceNotFoundException;
import com.tm.financialTransfer.mapper.FinancialTransferMapper;
import com.tm.financialTransfer.model.BankAccount;
import com.tm.financialTransfer.repository.BankAccountRepository;
import com.tm.financialTransfer.repository.FinancialTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<FinancialTransferDto> findAll() {

        logger.info("Finding all financial transfers!");

        return financialTransferMapper.listToDto(financialTransferRepository.findAll());
    }

    public FinancialTransferDto findById(Long id) {

        logger.info("Finding one financial transfer!");

        var entity = financialTransferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No results for this ID"));

        return financialTransferMapper.toDto(entity);
    }

    public FinancialTransferDto create(FinancialTransferDto financialTransferDto) {

        logger.info("Creating one financial transfer!");

        BankAccount sender = bankAccountRepository.findByAccountNumber(financialTransferDto.getSenderAccount());
        BankAccount beneficiary = bankAccountRepository.findByAccountNumber(financialTransferDto.getBeneficiaryAccount());

        var entity = financialTransferMapper.toModel(financialTransferDto, sender, beneficiary);
        entity = financialTransferRepository.save(entity);

        return financialTransferMapper.toDto(entity);
    }
}
