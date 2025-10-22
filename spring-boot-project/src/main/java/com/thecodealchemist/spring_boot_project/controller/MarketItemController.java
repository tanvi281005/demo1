package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.MarketItem;
import com.thecodealchemist.spring_boot_project.service.MarketItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/market-items")
public class MarketItemController {

    private final MarketItemService service;

    public MarketItemController(MarketItemService service) {
        this.service = service;
    }

    // DTO class to receive data from frontend
    public static class MarketItemRequestDTO {
        public String title;
        public String description;
        public String categoryName;
        public String price;
        public String itemCondition;
        public String photo;
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

    // ✅ New endpoint: Search items by title
    @GetMapping("/search")
    public List<MarketItem> searchItems(@RequestParam("q") String query) {
        return service.searchByTitle(query);
    }

    @PostMapping
    public ResponseEntity<MarketItem> createItem(@RequestBody MarketItemRequestDTO dto, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) {
            return ResponseEntity.status(401).build();
        }

        MarketItem item = new MarketItem();
        item.setTitle(dto.title);
        item.setDescription(dto.description);
        item.setCategoryName(dto.categoryName);
        try {
            item.setPrice(new BigDecimal(dto.price));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        item.setItemCondition(dto.itemCondition);
        item.setPhoto(dto.photo);
        item.setUserId(studentId);

        service.save(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) throw new RuntimeException("Login required");
        service.deleteById(id);
    }
}
