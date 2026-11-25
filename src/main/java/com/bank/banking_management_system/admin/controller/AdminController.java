package com.bank.banking_management_system.admin.controller;

import com.bank.banking_management_system.admin.service.AdminService;
import com.bank.banking_management_system.transaction.enums.TransactionType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", adminService.getDashboardStats());
        return "admin/dashboard";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", adminService.getAllCustomers());
        return "admin/customers";
    }

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts", adminService.getAllAccounts());
        return "admin/accounts";
    }

    @PostMapping("/accounts/{id}/freeze")
    public String freeze(@PathVariable Long id, RedirectAttributes ra) {
        return perform(ra, () -> adminService.freezeAccount(id), "Account frozen.");
    }

    @PostMapping("/accounts/{id}/unfreeze")
    public String unfreeze(@PathVariable Long id, RedirectAttributes ra) {
        return perform(ra, () -> adminService.unfreezeAccount(id), "Account unfrozen.");
    }

    @PostMapping("/accounts/{id}/close")
    public String close(@PathVariable Long id, RedirectAttributes ra) {
        return perform(ra, () -> adminService.closeAccount(id), "Account closed.");
    }

    @GetMapping("/transactions")
    public String transactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) TransactionType type,
            Model model) {
        model.addAttribute("transactions", adminService.filterTransactions(from, to, type));
        model.addAttribute("types", TransactionType.values());
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("selectedType", type);
        return "admin/transactions";
    }

    private String perform(RedirectAttributes ra, Runnable action, String successMsg) {
        try {
            action.run();
            ra.addFlashAttribute("success", successMsg);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/accounts";
    }
}
