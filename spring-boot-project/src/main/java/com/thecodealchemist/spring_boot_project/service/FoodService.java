package com.thecodealchemist.spring_boot_project.service;

import com.thecodealchemist.spring_boot_project.dao.FoodRepository;
import com.thecodealchemist.spring_boot_project.model.OrderItemDTO;
import com.thecodealchemist.spring_boot_project.model.FoodMenu;
import com.thecodealchemist.spring_boot_project.model.FoodOrder;
import com.thecodealchemist.spring_boot_project.model.Cart;
import com.thecodealchemist.spring_boot_project.model.CartItemDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

     public List<FoodMenu> getAllMenu() {
        return repository.getAllMenu();
    }

    public List<FoodMenu> getMenuByCulture(String culture) {
        return repository.getMenuByCulture(culture);
    }

    // public Cart getCart(int studentId) {
    //     return repository.getCartByStudent(studentId);
    // }

    @Transactional
    public void addToCart(int studentId, CartItemDTO item) {
        BigDecimal price = repository.getPriceByItemId(item.getItemId());
        repository.upsertCartItem(studentId, item.getItemId(), item.getQuantity(), price, item.getExtras(), item.getInstructions(), item.getPhoto());
        repository.updateCartTotals(studentId);
    }

    // ----------------- Place Final Order -----------------
    @Transactional
    public List<FoodOrder> placeOrders(int studentId, List<OrderItemDTO> items) {
        List<FoodOrder> placedOrders = new ArrayList<>();
        for (OrderItemDTO item : items) {
            if (item == null || item.getItemId() == null || item.getQuantity() == null || item.getQuantity() <= 0)
                continue;

            BigDecimal price = item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO;
            BigDecimal total = price.multiply(BigDecimal.valueOf(item.getQuantity()));

            int serviceId = repository.createService("Food Order for student " + studentId);
            repository.createFoodOrder(serviceId, item.getItemId(), item.getQuantity(), total, item.getExtras(), item.getInstructions());
            repository.createRequest(studentId, serviceId);

            FoodOrder fo = new FoodOrder();
            fo.setServiceId(serviceId);
            fo.setItemId(item.getItemId());
            fo.setQuantity(item.getQuantity());
            fo.setTotalPrice(total);
            fo.setExtras(item.getExtras());
            fo.setInstructions(item.getInstructions());
            placedOrders.add(fo);
        }
        return placedOrders;
    }

    // ----------------- Place Single Water Order -----------------
    @Transactional
    public FoodOrder placeSingleWaterOrder(int studentId, OrderItemDTO waterOrder) {
        if (waterOrder.getItemId() == null) waterOrder.setItemId(122); // water itemId
        if (waterOrder.getPrice() == null) waterOrder.setPrice(new BigDecimal("40"));
        if (waterOrder.getQuantity() == null) waterOrder.setQuantity(1);

        BigDecimal totalPrice = waterOrder.getPrice().multiply(BigDecimal.valueOf(waterOrder.getQuantity()));

        FoodOrder fo = new FoodOrder();
        fo.setItemId(waterOrder.getItemId());
        fo.setQuantity(waterOrder.getQuantity());
        fo.setTotalPrice(totalPrice);
        fo.setExtras(waterOrder.getExtras());
        fo.setInstructions(waterOrder.getInstructions());

        int serviceId = repository.createService("Water Order for Student " + studentId);
        fo.setServiceId(serviceId);

        repository.createFoodOrder(serviceId, fo.getItemId(), fo.getQuantity(), totalPrice, fo.getExtras(), fo.getInstructions());

        return fo;
    }
}
