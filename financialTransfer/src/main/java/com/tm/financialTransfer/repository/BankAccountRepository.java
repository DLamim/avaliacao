package com.tm.financialTransfer.repository;

import com.tm.financialTransfer.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findByAccountNumber(String accountNumber);

    List<BankAccount> findAllByBankName(String bankName);
}
