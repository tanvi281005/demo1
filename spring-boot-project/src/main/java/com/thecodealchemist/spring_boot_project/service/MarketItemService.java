package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import com.thecodealchemist.spring_boot_project.dao.MarketItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketItemService {

    private final MarketItemRepository repository;

    public MarketItemService(MarketItemRepository repository) {
        this.repository = repository;
    }

    public MarketItem save(MarketItem item) {
        return repository.save(item);
    }

    public List<MarketItem> findAll() {
        return (List<MarketItem>) repository.findAll();
    }

    public Optional<MarketItem> findById(Integer id) {
        return repository.findById(id);
    }

    public List<MarketItem> findByCategory(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    public List<MarketItem> search(String keyword) {
        return repository.searchByKeyword(keyword);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
