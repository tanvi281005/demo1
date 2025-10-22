package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.MarketTransactionRepository;
import com.thecodealchemist.spring_boot_project.dao.MarketItemRepository;
import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import com.thecodealchemist.spring_boot_project.model.MarketItem;
import com.thecodealchemist.spring_boot_project.model.TransactionViewDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketTransactionService {

    private final MarketTransactionRepository repository;
    private final MarketItemRepository itemRepository;

    public MarketTransactionService(MarketTransactionRepository repository, MarketItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public MarketTransaction createTransaction(int buyerId, int itemId, BigDecimal negotiatedPrice) {
        // Fetch the item
        MarketItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item does not exist"));

        if (item.getUserId().equals(buyerId)) {
            throw new RuntimeException("You cannot buy your own item");
        }

        // 1️⃣ Create service
        String serviceName = "Service for item " + itemId + " at " + LocalDateTime.now();
        int serviceId = repository.createService(serviceName);

        // 2️⃣ Create market transaction immediately
        BigDecimal originalPrice = item.getPrice();
        BigDecimal negotiated = negotiatedPrice != null ? negotiatedPrice : originalPrice;
        repository.createMarketTransaction(serviceId, itemId, negotiated, originalPrice);

        // 3️⃣ Create request linked to that transaction
        repository.createRequest(buyerId, serviceId);

        // Build and return MarketTransaction object
        MarketTransaction mt = new MarketTransaction();
        mt.setServiceId(serviceId);
        mt.setItemId(itemId);
        mt.setNegotiatedPrice(negotiated);
        mt.setOriginalPrice(originalPrice);
        mt.setFinalPrice(BigDecimal.ZERO); // default
        mt.setIsApproved(false);

        return mt;
    }

    public List<TransactionViewDTO> getTransactionsForSeller(int sellerId) {
        return repository.findTransactionsBySeller(sellerId);
    }

    public List<TransactionViewDTO> getTransactionsForBuyer(int buyerId) {
        return repository.findTransactionsByBuyer(buyerId);
    }

    public void updateFinalPrice(int serviceId, BigDecimal finalPrice) {
        repository.updateFinalPrice(serviceId, finalPrice);
    }

    public void approveTransaction(int serviceId, Boolean isApproved) {
        repository.approveTransaction(serviceId, isApproved);
    }
}
