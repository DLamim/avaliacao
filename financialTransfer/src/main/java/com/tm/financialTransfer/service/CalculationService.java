package com.tm.financialTransfer.service;

import com.tm.financialTransfer.exception.InvalidTransferDateIntervalException;
import com.tm.financialTransfer.exception.InvalidTransferTypeException;
import com.tm.financialTransfer.model.FinancialTransfer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Service
public class CalculationService {

    public FinancialTransfer calculateTax(FinancialTransfer transfer) throws InvalidTransferTypeException {

        Map<String, Function<FinancialTransfer, BigDecimal>> taxCalculators = new HashMap<>();
        taxCalculators.put("A", this::calculateTaxTypeA);
        taxCalculators.put("B", this::calculateTaxTypeB);
        taxCalculators.put("C", this::calculateTaxTypeC);
        taxCalculators.put("D", this::calculateTaxTypeD);

        String transferType = transfer.getType().toUpperCase(Locale.ROOT);

        BigDecimal calculatedTax = taxCalculators.getOrDefault(transferType, this::calculateTaxError).apply(transfer);
        BigDecimal totalAmount = transfer.getAmount().add(calculatedTax);
        transfer.setTax(calculatedTax);
        transfer.setTotalAmount(totalAmount);

        return transfer;
    }

    private BigDecimal calculateTaxTypeA(FinancialTransfer transfer) {
        //Accepted interval of 0 to 0
        calculateDaysDifference(transfer, 0, 0);

        BigDecimal tax = transfer.getAmount().multiply(BigDecimal.valueOf(0.03)).add(BigDecimal.valueOf(3));
        return tax.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTaxTypeB(FinancialTransfer transfer) {
        //Accepted interval of 1 to 10
        calculateDaysDifference(transfer, 1, 10);

        return BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTaxTypeC(FinancialTransfer transfer) {
        //Accepted interval of 11 to 10
        long daysDiff = calculateDaysDifference(transfer, 11, 365);

        BigDecimal tax;

        if (daysDiff >= 11) {
            if (daysDiff < 20) {
                tax = BigDecimal.valueOf(0.082);
            } else if (daysDiff < 30) {
                tax = BigDecimal.valueOf(0.069);
            } else if (daysDiff < 40) {
                tax = BigDecimal.valueOf(0.047);
            } else {
                tax = BigDecimal.valueOf(0.017);
            }
        } else {
            tax = BigDecimal.ZERO;
        }

        BigDecimal amount = transfer.getAmount();
        BigDecimal taxValue = amount.multiply(tax);
        return taxValue.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTaxTypeD(FinancialTransfer transfer) {
        BigDecimal amount = transfer.getAmount();
        BigDecimal tax;

        if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            tax = calculateTaxTypeA(transfer);
        } else if (amount.compareTo(BigDecimal.valueOf(2000)) <= 0) {
            tax = calculateTaxTypeB(transfer);
        } else {
            tax = calculateTaxTypeC(transfer);
        }

        return tax;
    }

    private long calculateDaysDifference(FinancialTransfer financialTransfer, long minimumInterval, long maximumInterval) throws InvalidTransferDateIntervalException {
        long daysDiff = ChronoUnit.DAYS.between(financialTransfer.getTransferDate().toLocalDate(), financialTransfer.getScheduleDate().toLocalDate());

        if (financialTransfer.getType().equals("D")) {
            return daysDiff;
        } else if (daysDiff < minimumInterval || daysDiff > maximumInterval) {
            throw new InvalidTransferDateIntervalException("Invalid transfer type according to date interval informed.");
        }

        return daysDiff;
    }

    private BigDecimal calculateTaxError(FinancialTransfer transfer) throws InvalidTransferTypeException {
        throw new InvalidTransferTypeException("Invalid transfer type.");
    }
}
