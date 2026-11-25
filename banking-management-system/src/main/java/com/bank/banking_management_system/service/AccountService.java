
package com.bank.banking_management_system.service;

import com.bank.banking_management_system.repository.AccountRepository;
import com.bank.banking_management_system.repository.CustomerProfileRepository;
import com.bank.banking_management_system.entity.Account;
import com.bank.banking_management_system.entity.CustomerProfile;
import com.bank.banking_management_system.dto.AccountRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerProfileRepository customerProfileRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public AccountService(
            AccountRepository accountRepository,
            CustomerProfileRepository customerProfileRepository) {

        this.accountRepository = accountRepository;
        this.customerProfileRepository = customerProfileRepository;
    }

    // Generate unique 12-digit account number
    private String generateAccountNumber() {

        String accountNumber;

        do {
            StringBuilder number = new StringBuilder();

            number.append(secureRandom.nextInt(9) + 1);

            for (int i = 1; i < 12; i++) {
                number.append(secureRandom.nextInt(10));
            }

            accountNumber = number.toString();

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    // Open a new account
    public Account openAccount(AccountRequest request, String email) {

        CustomerProfile customer = customerProfileRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Customer profile not found"));

        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");
        account.setCustomer(customer);

        return accountRepository.save(account);
    }

    // Get all accounts of a customer
    public List<Account> getCustomerAccounts(String email) {

        return accountRepository.findByCustomerUserEmail(email);
    }

    // Check account balance
    public BigDecimal getAccountBalance(Long accountId, String email) {

        Account account = accountRepository
                .findByIdAndCustomerUserEmail(accountId, email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found for this customer"
                ));

        return account.getBalance();
    }

    // Update account status
    public Account updateAccountStatus(Long accountId, String status) {

        if (status == null ||
                !(status.equals("ACTIVE") ||
                        status.equals("INACTIVE") ||
                        status.equals("FROZEN") ||
                        status.equals("CLOSED"))) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid account status"
            );
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException(
                        "Account not found"));

        account.setStatus(status);

        return accountRepository.save(account);
    }
}
