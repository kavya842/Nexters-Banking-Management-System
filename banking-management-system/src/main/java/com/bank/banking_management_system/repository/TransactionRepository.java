
package com.bank.banking_management_system.repository;

import com.bank.banking_management_system.entity.Transaction;
import com.bank.banking_management_system.enums.TransactionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions: newest first
    List<Transaction> findAllByOrderByTimestampDesc();

    // Filter transactions by type: newest first
    List<Transaction> findByTypeOrderByTimestampDesc(TransactionType type);

    // Filter transactions between two dates: newest first
    List<Transaction> findByTimestampBetweenOrderByTimestampDesc(
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
