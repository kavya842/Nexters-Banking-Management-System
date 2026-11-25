
package com.bank.banking_management_system.repository;

import com.bank.banking_management_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByStatus(String status);

    List<Account> findByCustomerUserEmail(String email);

    Optional<Account> findByIdAndCustomerUserEmail(Long id, String email);
}
