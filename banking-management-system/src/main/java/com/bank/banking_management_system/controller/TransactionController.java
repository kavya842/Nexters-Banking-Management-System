
package com.bank.banking_management_system.controller;

import com.bank.banking_management_system.dto.TransactionHistoryResponse;
import com.bank.banking_management_system.dto.TransactionRequest;
import com.bank.banking_management_system.dto.TransactionResponse;
import com.bank.banking_management_system.entity.Transaction;
import com.bank.banking_management_system.enums.TransactionType;
import com.bank.banking_management_system.service.TransactionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Record a transaction
    @PostMapping("/record")
    public ResponseEntity<TransactionResponse> recordTransaction(
            @Valid @RequestBody TransactionRequest request,
            @RequestParam TransactionType type) {

        Transaction transaction = transactionService.recordTransaction(
                type,
                request.getAmount()
        );

        TransactionResponse response = new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );

        return ResponseEntity.ok(response);
    }

    // Get all transaction history
    @GetMapping("/history")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory() {

        return ResponseEntity.ok(
                transactionService.getAllTransactionHistory()
        );
    }

    // Filter transactions by type
    @GetMapping("/filter")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionsByType(
            @RequestParam TransactionType type) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByType(type)
        );
    }

    // Filter transactions by date range
    @GetMapping("/filter/date")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByDateRange(
                        startDate,
                        endDate
                )
        );
    }
}
