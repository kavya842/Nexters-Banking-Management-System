package com.bank.banking_management_system.admin.Repository;

import com.bank.banking_management_system.account.entity.*;
import com.bank.banking_management_system.account.enums.AccountStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AdminRepository extends JpaRepository<Account, Long> {

    long countByStatus(AccountStatus status);

    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM Account a " +
           "WHERE a.status <> com.bank.banking_management_system.account.enums.AccountStatus.CLOSED")
    BigDecimal sumOpenBalances();
}