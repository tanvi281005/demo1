package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.service.MarketTransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

@RestController
@RequestMapping("/transactions")
public class MarketTransactionController {

    private final MarketTransactionService service;

    public MarketTransactionController(MarketTransactionService service) {
        this.service = service;
    }

    public static class TransactionRequest {
        public int itemId; // frontend sends only itemId
        public BigDecimal negotiatedPrice; // optional
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestTransaction(@RequestBody TransactionRequest request, HttpSession session) {
        Integer buyerId = (Integer) session.getAttribute("studentId");
        if (buyerId == null) return ResponseEntity.status(401).body("Login required");

        System.out.println("Buyer ID from session: " + buyerId); // ✅ debug

        try {
            MarketTransaction mt = service.createTransaction(buyerId, request.itemId, request.negotiatedPrice);
            return ResponseEntity.ok("Transaction requested! Service ID: " + mt.getServiceId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/seller")
    public List<MarketTransaction> getSellerTransactions(HttpSession session) {
        Integer sellerId = (Integer) session.getAttribute("studentId");
        if (sellerId == null) throw new RuntimeException("Login required");

        System.out.println("Seller ID from session: " + sellerId); // ✅ debug
        return service.getTransactionsForSeller(sellerId);
    }

    @GetMapping("/buyer")
    public List<MarketTransaction> getBuyerTransactions(HttpSession session) {
        Integer buyerId = (Integer) session.getAttribute("studentId");
        if (buyerId == null) throw new RuntimeException("Login required");

        System.out.println("Buyer ID from session: " + buyerId); // ✅ debug
        return service.getTransactionsForBuyer(buyerId);
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
