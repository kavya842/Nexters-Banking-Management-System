
package com.bank.banking_management_system.controller;

import com.bank.banking_management_system.dto.AccountRequest;
import com.bank.banking_management_system.entity.Account;
import com.bank.banking_management_system.service.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    private MockMvc mockMvc;

    private AccountService accountService;

    private Authentication authentication;

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {

        accountService = mock(AccountService.class);
        authentication = mock(Authentication.class);

        AccountController accountController =
                new AccountController(accountService);

        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setValidator(validator)
                .build();

        when(authentication.getName())
                .thenReturn("customer@test.com");
    }

    // 1. Test opening a savings account
    @Test
    void testOpenAccount() throws Exception {

        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456789012");
        account.setAccountType("SAVINGS");
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");

        when(accountService.openAccount(
                any(AccountRequest.class),
                eq("customer@test.com")
        )).thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountType\":\"SAVINGS\"}"))
                .andExpect(status().isCreated());

        verify(accountService).openAccount(
                any(AccountRequest.class),
                eq("customer@test.com")
        );
    }

    // 2. Test viewing customer accounts
    @Test
    void testGetCustomerAccounts() throws Exception {

        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456789012");
        account.setAccountType("SAVINGS");
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");

        when(accountService.getCustomerAccounts(
                "customer@test.com"
        )).thenReturn(List.of(account));

        mockMvc.perform(get("/api/accounts/my-accounts")
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(accountService)
                .getCustomerAccounts("customer@test.com");
    }

    // 3. Test checking account balance
    @Test
    void testGetAccountBalance() throws Exception {

        when(accountService.getAccountBalance(
                1L, "customer@test.com"
        )).thenReturn(new BigDecimal("5000.00"));

        mockMvc.perform(get("/api/accounts/1/balance")
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(accountService)
                .getAccountBalance(1L, "customer@test.com");
    }

    // 4. Test updating account status
    @Test
    void testUpdateAccountStatus() throws Exception {

        Account account = new Account();
        account.setId(1L);
        account.setStatus("INACTIVE");

        when(accountService.updateAccountStatus(1L, "INACTIVE"))
                .thenReturn(account);

        mockMvc.perform(put("/api/accounts/1/status")
                        .param("status", "INACTIVE"))
                .andExpect(status().isOk());

        verify(accountService)
                .updateAccountStatus(1L, "INACTIVE");
    }

    // 5. Test opening account without account type
    @Test
    void testOpenAccountWithMissingAccountType() throws Exception {

        mockMvc.perform(post("/api/accounts")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .openAccount(any(AccountRequest.class), anyString());
    }

    // 6. Test opening account with invalid account type
    @Test
    void testOpenAccountWithInvalidAccountType() throws Exception {

        mockMvc.perform(post("/api/accounts")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountType\":\"INVALID\"}"))
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .openAccount(any(AccountRequest.class), anyString());
    }
}
