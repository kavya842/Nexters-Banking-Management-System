
package com.bank.banking_management_system.dto;

import com.bank.banking_management_system.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponse {

    private Long transactionId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransactionHistoryResponse(
            Long transactionId,
            TransactionType type,
            BigDecimal amount,
            LocalDateTime timestamp) {

        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

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
}
