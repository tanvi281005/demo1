package com.thecodealchemist.spring_boot_project.controller;

import com.thecodealchemist.spring_boot_project.model.OrderItemDTO;
import com.thecodealchemist.spring_boot_project.model.OrdersRequest;
import com.thecodealchemist.spring_boot_project.model.FoodMenu;
import com.thecodealchemist.spring_boot_project.model.FoodOrder;
import com.thecodealchemist.spring_boot_project.model.Cart;
import com.thecodealchemist.spring_boot_project.model.CartItemDTO;
import com.thecodealchemist.spring_boot_project.service.FoodService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<FoodMenu>> getMenu(@RequestParam(required = false) String culture) {
        List<FoodMenu> menu = (culture == null || culture.isEmpty())
                ? service.getAllMenu()
                : service.getMenuByCulture(culture);
        return ResponseEntity.ok(menu);
    }

   @GetMapping("/cart")
    public ResponseEntity<Cart> getCart(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(service.getCart(studentId));
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDTO item, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        System.out.println("addToCart called; session studentId=" + studentId + " payload=" + item);
        if (studentId == null) return ResponseEntity.status(401).build();

        service.addToCart(studentId, item);
        return ResponseEntity.ok("yea");
    }

    @PostMapping("/order")
    public ResponseEntity<List<FoodOrder>> placeOrder(@RequestBody OrdersRequest request, HttpSession session) {
        System.out.println("📦 Received order request: " + request);
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) return ResponseEntity.status(401).build();

        List<FoodOrder> placed = service.placeOrders(studentId, request.getOrders());
        return ResponseEntity.ok(placed);
    }

    // ✅ Existing endpoint: Water order
    @PostMapping("/order/water")
    public ResponseEntity<FoodOrder> orderWater(@RequestBody OrderItemDTO waterOrder, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        if (studentId == null) return ResponseEntity.status(401).build();

        waterOrder.setItemId(122); // water itemId
        waterOrder.setPrice(new BigDecimal("40"));
        waterOrder.setQuantity(1);

        FoodOrder placed = service.placeSingleWaterOrder(studentId, waterOrder);
        return ResponseEntity.ok(placed);
    }
}
