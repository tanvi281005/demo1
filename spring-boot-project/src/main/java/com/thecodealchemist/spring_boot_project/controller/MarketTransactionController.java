package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.service.MarketTransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class MarketTransactionController {

    private final MarketTransactionService service;

    public MarketTransactionController(MarketTransactionService service) {
        this.service = service;
    }

    @PostMapping
    public void createTransaction(@RequestBody MarketTransaction transaction) {
        service.createTransaction(transaction);
    }

    @PutMapping("/{serviceId}/final-price")
    public void updateFinalPrice(@PathVariable Integer serviceId, @RequestParam BigDecimal finalPrice) {
        service.updateFinalPrice(serviceId, finalPrice);
    }

    @PutMapping("/{serviceId}/approve")
    public void approveTransaction(@PathVariable Integer serviceId, @RequestParam Boolean isApproved) {
        service.approveTransaction(serviceId, isApproved);
    }

    @GetMapping
    public List<MarketTransaction> getAllTransactions() {
        return service.getAllTransactions();
    }
}
