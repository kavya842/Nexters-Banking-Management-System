package com.bank.banking_management_system.admin.service;

import com.bank.banking_management_system.account.entity.Account;
import com.bank.banking_management_system.account.enums.AccountStatus;
import com.bank.banking_management_system.account.repository.AccountRepository;

import com.bank.banking_management_system.admin.dto.AdminDashboardResponse;
import com.bank.banking_management_system.admin.Repository.AdminRepository;

import com.bank.banking_management_system.common.exception.ResourceNotFoundException;

import com.bank.banking_management_system.customer.entity.CustomerProfile;
import com.bank.banking_management_system.customer.repository.CustomerRepository;

import com.bank.banking_management_system.transaction.entity.Transaction;
import com.bank.banking_management_system.transaction.enums.TransactionType;
import com.bank.banking_management_system.transaction.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AdminRepository adminRepository;

    // Constructor Injection
    public AdminService(CustomerRepository customerRepository,
                        AccountRepository accountRepository,
                        TransactionRepository transactionRepository,
                        AdminRepository adminRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.adminRepository = adminRepository;
    }

    // ---------- Dashboard ----------

    public AdminDashboardResponse getDashboardStats() {

        return new AdminDashboardResponse(
                customerRepository.count(),
                accountRepository.count(),
                adminRepository.countByStatus(AccountStatus.ACTIVE),
                adminRepository.countByStatus(AccountStatus.FROZEN),
                adminRepository.countByStatus(AccountStatus.CLOSED),
                adminRepository.sumOpenBalances()
        );
    }

    // ---------- View all Customers ----------

    public List<CustomerProfile> getAllCustomers() {

        return customerRepository.findAll();
    }

    // ---------- View all Accounts ----------

    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }

    // ---------- Freeze Account ----------

    @Transactional
    public void freezeAccount(Long accountId) {

        Account account = findAccount(accountId);

        if (account.getStatus() == AccountStatus.CLOSED) {

            throw new IllegalStateException(
                    "A closed account cannot be frozen."
            );
        }

        account.setStatus(AccountStatus.FROZEN);

        accountRepository.save(account);
    }

    // ---------- Unfreeze Account ----------

    @Transactional
    public void unfreezeAccount(Long accountId) {

        Account account = findAccount(accountId);

        if (account.getStatus() != AccountStatus.FROZEN) {

            throw new IllegalStateException(
                    "Only frozen accounts can be unfrozen."
            );
        }

        account.setStatus(AccountStatus.ACTIVE);

        accountRepository.save(account);
    }

    // ---------- Close Account ----------

    @Transactional
    public void closeAccount(Long accountId) {

        Account account = findAccount(accountId);

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {

            throw new IllegalStateException(
                    "Account balance must be zero before closing."
            );
        }

        account.setStatus(AccountStatus.CLOSED);

        accountRepository.save(account);
    }

    // ---------- Filter Transactions ----------

    public List<Transaction> filterTransactions(
            LocalDate from,
            LocalDate to,
            TransactionType type) {

        return transactionRepository.findAll()
                .stream()
                .filter(t -> type == null || t.getType() == type)
                .filter(t -> from == null ||
                        !t.getTimestamp().toLocalDate().isBefore(from))
                .filter(t -> to == null ||
                        !t.getTimestamp().toLocalDate().isAfter(to))
                .sorted(
                        Comparator.comparing(Transaction::getTimestamp)
                                .reversed()
                )
                .collect(Collectors.toList());
    }

    // ---------- Find Account By ID ----------

    private Account findAccount(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + id
                        )
                );
    }
}