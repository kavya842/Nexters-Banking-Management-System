package com.bank.banking_management_system.admin.dto;

import java.math.BigDecimal;

public class AdminDashboardResponse {

    private long totalCustomers;
    private long totalAccounts;
    private long activeAccounts;
    private long frozenAccounts;
    private long closedAccounts;
    private BigDecimal totalBalance;

    public AdminDashboardResponse(long totalCustomers,
                                  long totalAccounts,
                                  long activeAccounts,
                                  long frozenAccounts,
                                  long closedAccounts,
                                  BigDecimal totalBalance) {

        this.totalCustomers = totalCustomers;
        this.totalAccounts = totalAccounts;
        this.activeAccounts = activeAccounts;
        this.frozenAccounts = frozenAccounts;
        this.closedAccounts = closedAccounts;
        this.totalBalance = totalBalance;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public long getTotalAccounts() {
        return totalAccounts;
    }

    public long getActiveAccounts() {
        return activeAccounts;
    }

    public long getFrozenAccounts() {
        return frozenAccounts;
    }

    public long getClosedAccounts() {
        return closedAccounts;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }
}