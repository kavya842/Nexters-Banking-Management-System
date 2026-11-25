
package com.bank.banking_management_system.service;

import com.bank.banking_management_system.dto.TransactionHistoryResponse;
import com.bank.banking_management_system.entity.Transaction;
import com.bank.banking_management_system.enums.TransactionType;
import com.bank.banking_management_system.exception.InvalidTransactionException;
import com.bank.banking_management_system.repository.TransactionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    // 1. Valid transaction should be saved
    @Test
    void shouldRecordValidTransaction() {

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction result = transactionService.recordTransaction(
                TransactionType.DEPOSIT,
                new BigDecimal("1000")
        );

        assertNotNull(result);
        assertEquals(TransactionType.DEPOSIT, result.getType());
        assertEquals(new BigDecimal("1000"), result.getAmount());
        assertNotNull(result.getTimestamp());

        verify(transactionRepository, times(1))
                .save(any(Transaction.class));
    }

    // 2. Zero amount should be rejected
    @Test
    void shouldRejectZeroAmount() {

        assertThrows(
                InvalidTransactionException.class,
                () -> transactionService.recordTransaction(
                        TransactionType.DEPOSIT,
                        BigDecimal.ZERO
                )
        );

        verify(transactionRepository, never()).save(any());
    }

    // 3. Negative amount should be rejected
    @Test
    void shouldRejectNegativeAmount() {

        assertThrows(
                InvalidTransactionException.class,
                () -> transactionService.recordTransaction(
                        TransactionType.DEPOSIT,
                        new BigDecimal("-500")
                )
        );

        verify(transactionRepository, never()).save(any());
    }

    // 4. Null amount should be rejected
    @Test
    void shouldRejectNullAmount() {

        assertThrows(
                InvalidTransactionException.class,
                () -> transactionService.recordTransaction(
                        TransactionType.DEPOSIT,
                        null
                )
        );

        verify(transactionRepository, never()).save(any());
    }

    // 5. Null transaction type should be rejected
    @Test
    void shouldRejectNullTransactionType() {

        assertThrows(
                InvalidTransactionException.class,
                () -> transactionService.recordTransaction(
                        null,
                        new BigDecimal("500")
                )
        );

        verify(transactionRepository, never()).save(any());
    }

    // 6. Transaction history should be retrieved
    @Test
    void shouldReturnTransactionHistory() {

        Transaction transaction = new Transaction(
                TransactionType.DEPOSIT,
                new BigDecimal("1000")
        );

        when(transactionRepository.findAllByOrderByTimestampDesc())
                .thenReturn(List.of(transaction));

        List<TransactionHistoryResponse> result =
                transactionService.getAllTransactionHistory();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(transactionRepository, times(1))
                .findAllByOrderByTimestampDesc();
    }

    // 7. Empty transaction history should return empty list
    @Test
    void shouldReturnEmptyTransactionHistory() {

        when(transactionRepository.findAllByOrderByTimestampDesc())
                .thenReturn(List.of());

        List<TransactionHistoryResponse> result =
                transactionService.getAllTransactionHistory();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(transactionRepository, times(1))
                .findAllByOrderByTimestampDesc();
    }
}
