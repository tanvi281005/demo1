package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.MarketItemRepository;
import com.thecodealchemist.spring_boot_project.model.MarketItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketItemService {

    private final MarketItemRepository repository;

    public MarketItemService(MarketItemRepository repository) {
        this.repository = repository;
    }

    public void save(MarketItem item) {
        repository.save(item);
    }

    public List<MarketItem> findAll() {
        return repository.findAll();
    }

    public Optional<MarketItem> findById(Integer id) {
        return repository.findById(id);
    }

    public List<MarketItem> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // ✅ New service method for search
    public List<MarketItem> searchByTitle(String keyword) {
        return repository.searchByTitle(keyword);
    }
}
