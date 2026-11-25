package com.bank.banking_management_system.controller;

import com.bank.banking_management_system.dto.CustomerResponse;
import com.bank.banking_management_system.service.CustomerProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
public class AdminCustomerController {

    private final CustomerProfileService customerProfileService;

    public AdminCustomerController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerProfileService.getAllCustomers();
    }
}
