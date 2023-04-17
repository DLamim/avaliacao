package com.tm.financialTransfer.mapper;

import com.tm.financialTransfer.dto.FinancialTransferDto;
import com.tm.financialTransfer.model.BankAccount;
import com.tm.financialTransfer.model.FinancialTransfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinancialTransferMapper {

    public FinancialTransferDto toDto(FinancialTransfer transfer) {
        FinancialTransferDto dto = new FinancialTransferDto();
        BankAccount senderAccount = transfer.getSender();

        dto.setId(transfer.getId());
        dto.setSenderAccount(transfer.getSender().getAccountNumber());
        dto.setBeneficiaryAccount(transfer.getBeneficiary().getAccountNumber());
        dto.setAmount(transfer.getAmount());
        dto.setTax(transfer.getTax());
        dto.setTransferDate(transfer.getTransferDate());
        dto.setScheduleDate(transfer.getScheduleDate());
        return dto;
    }

    public FinancialTransfer toModel(FinancialTransferDto dto, BankAccount senderAccount, BankAccount beneficiaryAccount) {
        FinancialTransfer transfer = new FinancialTransfer();
        transfer.setId(dto.getId());
        transfer.setSender(senderAccount);
        transfer.setBeneficiary(beneficiaryAccount);
        transfer.setAmount(dto.getAmount());
        transfer.setTax(dto.getTax());
        transfer.setTransferDate(dto.getTransferDate());
        transfer.setScheduleDate(dto.getScheduleDate());
        return transfer;
    }

    public List<FinancialTransferDto> listToDto(List<FinancialTransfer> financialTransferList) {
        List<FinancialTransferDto> dtoList = new ArrayList<>();

        financialTransferList.stream().forEach(o -> dtoList.add(toDto(o)));

        return dtoList;
    }
}
