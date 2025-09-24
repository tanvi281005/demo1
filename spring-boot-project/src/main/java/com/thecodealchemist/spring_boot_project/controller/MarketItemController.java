package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import com.thecodealchemist.spring_boot_project.service.MarketItemService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/category/{category}")
    public List<MarketItem> getItemsByCategory(@PathVariable String category) {
        return service.findByCategory(category);
    }

    @PostMapping
    public MarketItem createItem(@RequestBody MarketItem item, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) throw new RuntimeException("Login required");
        item.setUserId(studentId);
        service.save(item);
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) throw new RuntimeException("Login required");
        service.deleteById(id);
    }
}
