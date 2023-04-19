package com.tm.financialTransfer.service;

import com.tm.financialTransfer.exception.InvalidTransferDateIntervalException;
import com.tm.financialTransfer.exception.InvalidTransferTypeException;
import com.tm.financialTransfer.model.FinancialTransfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
class CalculationServiceTest {

    private CalculationService calculationService;
    private FinancialTransfer financialTransfer;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
        financialTransfer = new FinancialTransfer();
        financialTransfer.setAmount(BigDecimal.valueOf(1000));
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(5));
        financialTransfer.setTransferDate(LocalDateTime.now());
        financialTransfer.setType("A");
    }

    @Test
    void testCalculateTaxTypeA() {
        financialTransfer.setScheduleDate(LocalDateTime.now());
        financialTransfer.setType("A");

        BigDecimal expectedTax = BigDecimal.valueOf(33).setScale(2, RoundingMode.HALF_UP);

        financialTransfer.setAmount(BigDecimal.valueOf(1000));
        financialTransfer = calculationService.calculateTax(financialTransfer);
        Assertions.assertEquals(expectedTax, financialTransfer.getTax());
    }

    @Test
    void testCalculateTaxTypeB() {
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(9));
        financialTransfer.setType("B");

        BigDecimal expectedTax = BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP);
        financialTransfer = calculationService.calculateTax(financialTransfer);
        Assertions.assertEquals(expectedTax, financialTransfer.getTax());
    }

    @Test
    void testCalculateTaxTypeC() {
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(11));
        financialTransfer.setType("C");

        BigDecimal expectedTax = BigDecimal.valueOf(82).setScale(2, RoundingMode.HALF_UP);
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(11));
        financialTransfer = calculationService.calculateTax(financialTransfer);
        Assertions.assertEquals(expectedTax, financialTransfer.getTax());
    }

    @Test
    void testCalculateTaxTypeD() {
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(11));
        financialTransfer.setType("D");

        BigDecimal expectedTax = BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP);
        financialTransfer.setAmount(BigDecimal.valueOf(1500));
        financialTransfer = calculationService.calculateTax(financialTransfer);
        Assertions.assertEquals(expectedTax, financialTransfer.getTax());
    }

    @Test
    void testCalculateTaxError() {
        financialTransfer.setType("Z");
        Assertions.assertThrows(InvalidTransferTypeException.class, () -> calculationService.calculateTax(financialTransfer));
    }

    @Test
    void testCalculateDaysDifferenceError() {
        financialTransfer.setType("A");
        financialTransfer.setScheduleDate(LocalDateTime.now().plusDays(20));
        Assertions.assertThrows(InvalidTransferDateIntervalException.class, () -> calculationService.calculateTax(financialTransfer));
    }

}
