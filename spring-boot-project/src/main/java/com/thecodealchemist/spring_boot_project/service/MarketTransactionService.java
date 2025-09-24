package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.MarketTransactionRepository;
import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketTransactionService {

    private final MarketTransactionRepository repository;

    public MarketTransactionService(MarketTransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MarketTransaction createTransaction(int itemId, BigDecimal negotiatedPrice, BigDecimal originalPrice) {
        if (!repository.itemExists(itemId)) {
            throw new RuntimeException("Item does not exist");
        }

        // Auto-generate service name
        String serviceName = "Service for item " + itemId + " at " + LocalDateTime.now();
        int serviceId = repository.createService(serviceName);

        repository.createMarketTransaction(serviceId, itemId, negotiatedPrice, originalPrice);

        MarketTransaction mt = new MarketTransaction();
        mt.setServiceId(serviceId);
        mt.setItemId(itemId);
        mt.setNegotiatedPrice(negotiatedPrice);
        mt.setOriginalPrice(originalPrice);
        mt.setFinalPrice(BigDecimal.ZERO);
        mt.setIsApproved(false);
        return mt;
    }

    public List<MarketTransaction> getTransactionsForSeller(int sellerId) {
        return repository.findTransactionsBySeller(sellerId);
    }

    public void updateFinalPrice(int serviceId, BigDecimal finalPrice) {
        repository.updateFinalPrice(serviceId, finalPrice);
    }

    public void approveTransaction(int serviceId, boolean isApproved) {
        repository.approveTransaction(serviceId, isApproved);
    }
}
