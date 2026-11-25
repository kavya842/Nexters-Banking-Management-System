package com.bank.banking_management_system.customer.repository;

import com.bank.banking_management_system.customer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<CustomerProfile, Long> {

}