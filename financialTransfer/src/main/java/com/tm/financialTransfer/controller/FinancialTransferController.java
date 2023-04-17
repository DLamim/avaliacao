package com.tm.financialTransfer.controller;

import com.tm.financialTransfer.dto.FinancialTransferDto;
import com.tm.financialTransfer.service.FinancialTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financialTransfer")
public class FinancialTransferController {

    @Autowired
    private FinancialTransferService financialTransferService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FinancialTransferDto> findAll() {
        return financialTransferService.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialTransferDto findById(@PathVariable(value = "id") Long id) {
        return financialTransferService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialTransferDto create(@RequestBody FinancialTransferDto person) {
        return financialTransferService.create(person);
    }
}
