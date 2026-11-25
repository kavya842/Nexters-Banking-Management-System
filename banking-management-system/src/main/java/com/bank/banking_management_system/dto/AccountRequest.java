
package com.bank.banking_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AccountRequest {

    @NotBlank(message = "Account type is required")
    @Pattern(
            regexp = "SAVINGS|CURRENT",
            message = "Account type must be SAVINGS or CURRENT"
    )
    private String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
