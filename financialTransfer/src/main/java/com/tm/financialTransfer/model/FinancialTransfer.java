package com.tm.financialTransfer.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "financial_transfer")
public class FinancialTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderID", nullable = false)
    private BankAccount sender;

    @ManyToOne
    @JoinColumn(name = "beneficiaryID", nullable = false)
    private BankAccount beneficiary;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "tax", nullable = false)
    private BigDecimal tax;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "transfer_type", nullable = false)
    private String type;

    @Column(name = "transfer_date", nullable = false)
    private Date transferDate;

    @Column(name = "schedule_date", nullable = false)
    private Date scheduleDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccount getSender() {
        return sender;
    }

    public void setSender(BankAccount senderID) {
        this.sender = senderID;
    }

    public BankAccount getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BankAccount beneficiary) {
        this.beneficiary = beneficiary;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
