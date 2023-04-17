package com.tm.financialTransfer.repository;

import com.tm.financialTransfer.model.FinancialTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransferRepository extends JpaRepository<FinancialTransfer, Long> {
}
