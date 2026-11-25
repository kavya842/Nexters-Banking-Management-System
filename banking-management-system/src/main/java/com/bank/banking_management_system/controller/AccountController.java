
package com.bank.banking_management_system.controller;

import com.bank.banking_management_system.entity.Account;
import com.bank.banking_management_system.dto.AccountRequest;
import com.bank.banking_management_system.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 1. Open a new account
    @PostMapping
    public ResponseEntity<Account> openAccount(
            @Valid @RequestBody AccountRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Account account = accountService.openAccount(request, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // 2. Get all accounts of logged-in customer
    @GetMapping("/my-accounts")
    public ResponseEntity<List<Account>> getMyAccounts(
            Authentication authentication) {

        String email = authentication.getName();

        List<Account> accounts =
                accountService.getCustomerAccounts(email);

        return ResponseEntity.ok(accounts);
    }

    // 3. Check account balance
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @PathVariable Long accountId,
            Authentication authentication) {

        String email = authentication.getName();

        BigDecimal balance =
                accountService.getAccountBalance(accountId, email);

        return ResponseEntity.ok(balance);
    }

    // 4. Update account status - ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{accountId}/status")
    public ResponseEntity<Account> updateAccountStatus(
            @PathVariable Long accountId,
            @RequestParam String status) {

        Account account =
                accountService.updateAccountStatus(accountId, status);

        return ResponseEntity.ok(account);
    }
}
