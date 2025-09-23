package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import com.thecodealchemist.spring_boot_project.service.MarketItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/market-items")
public class MarketItemController {

    private final MarketItemService service;

    public MarketItemController(MarketItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<MarketItem> getAllItems() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<MarketItem> getItem(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/category/{categoryName}")
    public List<MarketItem> getItemsByCategory(@PathVariable String categoryName) {
        return service.findByCategory(categoryName);
    }

    @GetMapping("/search")
    public List<MarketItem> searchItems(@RequestParam String q) {
        return service.search(q);
    }

    @PostMapping
    public MarketItem createItem(@RequestBody MarketItem item) {
        return service.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
