package com.bank.banking_management_system.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password,
        @NotBlank String fullName,
        @NotBlank String phone,
        @NotBlank String address
) {}