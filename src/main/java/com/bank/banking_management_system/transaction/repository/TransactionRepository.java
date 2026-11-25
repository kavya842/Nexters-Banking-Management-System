package com.bank.banking_management_system.transaction.repository;

import com.bank.banking_management_system.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

}