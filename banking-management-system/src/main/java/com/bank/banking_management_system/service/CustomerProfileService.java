package com.bank.banking_management_system.service;

import com.bank.banking_management_system.dto.CustomerResponse;
import com.bank.banking_management_system.dto.RegisterRequest;
import com.bank.banking_management_system.entity.CustomerProfile;
import com.bank.banking_management_system.entity.User;
import com.bank.banking_management_system.repository.CustomerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                                                 // Spring manage chese service bean
public class CustomerProfileService {

    private final CustomerProfileRepository profileRepository;

    public CustomerProfileService(CustomerProfileRepository profileRepository) {
        this.profileRepository = profileRepository;      // Spring inject chestundi
    }

    @Transactional                                       // register transaction lo join avuthundi
    public CustomerProfile createProfile(User user, RegisterRequest req) {
        CustomerProfile profile = new CustomerProfile(
                req.fullName(), req.phone(), req.address(), user);
        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return profileRepository.findAll().stream()
                .map(p -> new CustomerResponse(
                        p.getId(),
                        p.getFullName(),
                        p.getUser().getEmail(),
                        p.getPhone(),
                        p.getAddress()))
                .toList();
    }
}
