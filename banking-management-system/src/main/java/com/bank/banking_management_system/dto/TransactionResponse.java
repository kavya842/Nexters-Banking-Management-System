
package com.bank.banking_management_system.dto;

import com.bank.banking_management_system.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long transactionId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransactionResponse() {
    }

    public TransactionResponse(
            Long transactionId,
            TransactionType type,
            BigDecimal amount,
            LocalDateTime timestamp) {

        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters
    public Long getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
