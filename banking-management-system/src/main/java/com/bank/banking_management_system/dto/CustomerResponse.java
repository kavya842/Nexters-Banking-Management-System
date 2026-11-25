package com.bank.banking_management_system.dto;

public record CustomerResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String address
) {}
