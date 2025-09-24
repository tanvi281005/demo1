package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.service.MarketTransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
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

    public static class TransactionRequest {
        public int itemId;
        public BigDecimal negotiatedPrice;
        public BigDecimal originalPrice;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestTransaction(@RequestBody TransactionRequest request,
                                                     HttpSession session) {
        try {
            Integer studentId = (Integer) session.getAttribute("studentId");
            if (studentId == null) return ResponseEntity.status(401).body("Login required");

            MarketTransaction mt = service.createTransaction(request.itemId, request.negotiatedPrice, request.originalPrice);
            return ResponseEntity.ok("Transaction requested! Service ID: " + mt.getServiceId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/seller")
    public List<MarketTransaction> getSellerTransactions(HttpSession session) {
        Integer sellerId = (Integer) session.getAttribute("studentId");
        if (sellerId == null) throw new RuntimeException("Login required");
        return service.getTransactionsForSeller(sellerId);
    }

    @PutMapping("/{serviceId}/final-price")
    public void updateFinalPrice(@PathVariable int serviceId, @RequestParam BigDecimal finalPrice) {
        service.updateFinalPrice(serviceId, finalPrice);
    }

    @PutMapping("/{serviceId}/approve")
    public void approveTransaction(@PathVariable int serviceId, @RequestParam boolean isApproved) {
        service.approveTransaction(serviceId, isApproved);
    }
}
