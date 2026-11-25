
package com.bank.banking_management_system.service;

import com.bank.banking_management_system.dto.AccountRequest;
import com.bank.banking_management_system.entity.Account;
import com.bank.banking_management_system.entity.CustomerProfile;
import com.bank.banking_management_system.repository.AccountRepository;
import com.bank.banking_management_system.repository.CustomerProfileRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @InjectMocks
    private AccountService accountService;

    private CustomerProfile customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerProfile();
    }

    // 1. Test opening a SAVINGS account
    @Test
    void testOpenSavingsAccount() {

        String email = "customer@test.com";

        AccountRequest request = new AccountRequest();
        request.setAccountType("SAVINGS");

        when(customerProfileRepository.findByUserEmail(email))
                .thenReturn(Optional.of(customer));

        when(accountRepository.existsByAccountNumber(anyString()))
                .thenReturn(false);

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.openAccount(request, email);

        assertNotNull(result);
        assertEquals("SAVINGS", result.getAccountType());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        assertEquals("ACTIVE", result.getStatus());
        assertNotNull(result.getAccountNumber());
        assertTrue(result.getAccountNumber().matches("\\d{12}"));

        verify(accountRepository).save(any(Account.class));
    }

    // 2. Test opening a CURRENT account
    @Test
    void testOpenCurrentAccount() {

        String email = "customer@test.com";

        AccountRequest request = new AccountRequest();
        request.setAccountType("CURRENT");

        when(customerProfileRepository.findByUserEmail(email))
                .thenReturn(Optional.of(customer));

        when(accountRepository.existsByAccountNumber(anyString()))
                .thenReturn(false);

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.openAccount(request, email);

        assertNotNull(result);
        assertEquals("CURRENT", result.getAccountType());
        assertEquals("ACTIVE", result.getStatus());

        verify(accountRepository).save(any(Account.class));
    }

    // 3. Test viewing customer accounts
    @Test
    void testGetCustomerAccounts() {

        String email = "customer@test.com";

        List<Account> accounts = List.of(
                new Account(),
                new Account()
        );

        when(accountRepository.findByCustomerUserEmail(email))
                .thenReturn(accounts);

        List<Account> result =
                accountService.getCustomerAccounts(email);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(accountRepository).findByCustomerUserEmail(email);
    }

    // 4. Test checking account balance
    @Test
    void testGetAccountBalance() {

        Long accountId = 1L;
        String email = "customer@test.com";

        Account account = new Account();
        account.setBalance(new BigDecimal("5000.00"));

        when(accountRepository.findByIdAndCustomerUserEmail(
                accountId, email))
                .thenReturn(Optional.of(account));

        BigDecimal result =
                accountService.getAccountBalance(accountId, email);

        assertEquals(new BigDecimal("5000.00"), result);

        verify(accountRepository)
                .findByIdAndCustomerUserEmail(accountId, email);
    }

    // 5. Test balance request for an account not owned by customer
    @Test
    void testGetAccountBalanceForUnauthorizedAccount() {

        Long accountId = 2L;
        String email = "customer@test.com";

        when(accountRepository.findByIdAndCustomerUserEmail(
                accountId, email))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> accountService.getAccountBalance(accountId, email)
        );
    }

    // 6. Test updating account status
    @Test
    void testUpdateAccountStatus() {

        Long accountId = 1L;

        Account account = new Account();
        account.setStatus("ACTIVE");

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Account result =
                accountService.updateAccountStatus(accountId, "INACTIVE");

        assertEquals("INACTIVE", result.getStatus());

        verify(accountRepository).save(account);
    }

    // 7. Test invalid account status
    @Test
    void testUpdateAccountStatusWithInvalidStatus() {

        assertThrows(
                ResponseStatusException.class,
                () -> accountService.updateAccountStatus(1L, "INVALID")
        );

        verify(accountRepository, never()).save(any(Account.class));
    }
}
