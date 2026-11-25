package com.bank.banking_management_system.security;

import com.bank.banking_management_system.entity.Role;
import com.bank.banking_management_system.entity.User;
import com.bank.banking_management_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@bank.com")) {
            userRepository.save(new User("admin@bank.com",
                    passwordEncoder.encode("Admin@123"), Role.ADMIN));
        }
    }
}