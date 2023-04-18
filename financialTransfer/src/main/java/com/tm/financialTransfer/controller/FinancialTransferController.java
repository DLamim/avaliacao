package com.tm.financialTransfer.controller;

import com.tm.financialTransfer.dto.FinancialTransferDto;
import com.tm.financialTransfer.service.FinancialTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/financialTransfer")
public class FinancialTransferController {

    @Autowired
    private FinancialTransferService financialTransferService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FinancialTransferDto> findAllFinancialTransfer() {
        return financialTransferService.findAllFinancialTransfer();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialTransferDto findFinancialTransferById(@PathVariable(value = "id") Long id) {
        return financialTransferService.findFinancialTransferById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinancialTransferDto> createFinancialTransfer(@RequestBody FinancialTransferDto person) throws AccountNotFoundException {
        FinancialTransferDto financialTransferDto = financialTransferService.createFinancialTransfer(person);
        return new ResponseEntity<>(financialTransferDto, HttpStatus.CREATED);
    }
}
