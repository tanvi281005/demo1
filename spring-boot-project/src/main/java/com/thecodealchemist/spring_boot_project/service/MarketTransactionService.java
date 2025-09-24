package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.MarketTransactionRepository;
import com.thecodealchemist.spring_boot_project.model.MarketTransaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MarketTransactionService {

    private final MarketTransactionRepository repository;

    public MarketTransactionService(MarketTransactionRepository repository) {
        this.repository = repository;
    }

    public void createTransaction(MarketTransaction transaction) {
        repository.createTransaction(transaction);
    }

    public void updateFinalPrice(Integer serviceId, BigDecimal finalPrice) {
        repository.updateFinalPrice(serviceId, finalPrice);
    }

    public void approveTransaction(Integer serviceId, Boolean isApproved) {
        repository.approveTransaction(serviceId, isApproved);
    }

    public List<MarketTransaction> getAllTransactions() {
        return repository.getAllTransactions();
    }
}
