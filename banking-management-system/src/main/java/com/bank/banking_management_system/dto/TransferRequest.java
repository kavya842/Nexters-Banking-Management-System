
package com.bank.banking_management_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferRequest {

    @NotNull(message = "Sender account ID is required")
    @Positive(message = "Invalid sender account ID")
    private Long fromAccountId;

    @NotNull(message = "Receiver account ID is required")
    @Positive(message = "Invalid receiver account ID")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Transfer amount must be greater than zero")
    private BigDecimal amount;

    public TransferRequest() {
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
