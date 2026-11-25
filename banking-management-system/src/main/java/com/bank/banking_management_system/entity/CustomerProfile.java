package com.bank.banking_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @OneToOne(fetch = FetchType.LAZY, optional = false)  // oka profile ki oka user (One-to-One)
    @JoinColumn(name = "user_id", nullable = false, unique = true) // foreign key column
    private User user;

    public CustomerProfile() {}                          // Hibernate ki kavali

    public CustomerProfile(String fullName, String phone, String address, User user) {
        this.fullName = fullName;                        // oke line lo object create cheyadaniki
        this.phone = phone;
        this.address = address;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
