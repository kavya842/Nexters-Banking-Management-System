
package com.bank.banking_management_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponse {

    private String message;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransferResponse(
            String message,
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount,
            LocalDateTime timestamp) {

        this.message = message;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
