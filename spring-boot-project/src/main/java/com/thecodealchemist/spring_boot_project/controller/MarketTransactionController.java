package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.service.MarketTransactionService;
import com.thecodealchemist.spring_boot_project.model.TransactionViewDTO;
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
    if (buyerId == null) {
        return ResponseEntity.status(401).body("Login required");
    }

    System.out.println("📩 / " + buyerId);
    System.out.println("📦 Item ID: " + request.itemId);
    System.out.println("💰 Negotiated Price: " + request.negotiatedPrice);

    try {
        MarketTransaction mt = service.createTransaction(buyerId, request.itemId, request.negotiatedPrice);
        System.out.println("✅ Transaction created, ID: " + mt.getServiceId());
        return ResponseEntity.ok("Transaction requested! Service ID: " + mt.getServiceId());
    } catch (RuntimeException e) {
        // ✅ Proper error logging
        e.printStackTrace(); // print full error details to console
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}

    @GetMapping("/seller")
public ResponseEntity<List<TransactionViewDTO>> getSellerTransactions(HttpSession session) {
    System.out.println("Session ID: " + session.getId());
    System.out.println("Student ID: " + session.getAttribute("studentId"));
    Integer sellerId = (Integer) session.getAttribute("studentId");
    if (sellerId == null) {
        System.out.println("❌ Unauthorized access");
        return ResponseEntity.status(401).build();
    }
    System.out.println("✅ Authorized as " + sellerId);
    List<TransactionViewDTO> list = service.getTransactionsForSeller(sellerId);
    return ResponseEntity.ok(list);
}


    @GetMapping("/buyer")
public ResponseEntity<List<TransactionViewDTO>> getBuyerTransactions(HttpSession session) {
    System.out.println("Session ID: " + session.getId());
    Integer buyerId = (Integer) session.getAttribute("studentId");
    System.out.println("Buyer ID from session: " + buyerId);
    if (buyerId == null) return ResponseEntity.status(401).build();

    List<TransactionViewDTO> list = service.getTransactionsForBuyer(buyerId);
    return ResponseEntity.ok(list);
}

    // @GetMapping("/seller")
    // public List<MarketTransaction> getSellerTransactions(HttpSession session) {
    //     Integer sellerId = (Integer) session.getAttribute("studentId");
    //     if (sellerId == null) throw new RuntimeException("Login required");

    //     System.out.println("Seller ID from session: " + sellerId); // ✅ debug
    //     return service.getTransactionsForSeller(sellerId);
    // }

    // @GetMapping("/buyer")
    // public List<MarketTransaction> getBuyerTransactions(HttpSession session) {
    //     Integer buyerId = (Integer) session.getAttribute("studentId");
    //     if (buyerId == null) throw new RuntimeException("Login required");

    //     System.out.println("Buyer ID from session: " + buyerId); // ✅ debug
    //     return service.getTransactionsForBuyer(buyerId);
    // }

    @PutMapping("/{serviceId}/final-price")
    public void updateFinalPrice(@PathVariable int serviceId, @RequestParam BigDecimal finalPrice) {
        service.updateFinalPrice(serviceId, finalPrice);
    }

    @PutMapping("/{serviceId}/approve")
    public void approveTransaction(@PathVariable int serviceId, @RequestParam Boolean isApproved) {
        service.approveTransaction(serviceId, isApproved);
    }
}
