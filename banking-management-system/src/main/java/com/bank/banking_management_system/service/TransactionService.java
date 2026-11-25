
package com.bank.banking_management_system.service;

import com.bank.banking_management_system.dto.TransactionHistoryResponse;
import com.bank.banking_management_system.entity.Transaction;
import com.bank.banking_management_system.enums.TransactionType;
import com.bank.banking_management_system.repository.TransactionRepository;
import com.bank.banking_management_system.exception.InvalidTransactionException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Record a transaction
    public Transaction recordTransaction(TransactionType type, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException(
                    "Transaction amount must be greater than zero"
            );
        }

        if (type == null) {
            throw new InvalidTransactionException(
                    "Transaction type is required"
            );
        }

        Transaction transaction = new Transaction(type, amount);

        return transactionRepository.save(transaction);
    }

    // Get all transaction history (newest first)
    public List<TransactionHistoryResponse> getAllTransactionHistory() {

        return transactionRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(transaction -> new TransactionHistoryResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    // Get transaction history filtered by type
    public List<TransactionHistoryResponse> getTransactionsByType(TransactionType type) {

        if (type == null) {
            throw new InvalidTransactionException(
                    "Transaction type is required"
            );
        }

        return transactionRepository.findByTypeOrderByTimestampDesc(type)
                .stream()
                .map(transaction -> new TransactionHistoryResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    // Get transactions between two dates
    public List<TransactionHistoryResponse> getTransactionsByDateRange(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        if (startDate == null || endDate == null) {
            throw new InvalidTransactionException(
                    "Start date and end date are required"
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidTransactionException(
                    "Start date cannot be after end date"
            );
        }

        return transactionRepository
                .findByTimestampBetweenOrderByTimestampDesc(startDate, endDate)
                .stream()
                .map(transaction -> new TransactionHistoryResponse(
                        transaction.getId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        transaction.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
