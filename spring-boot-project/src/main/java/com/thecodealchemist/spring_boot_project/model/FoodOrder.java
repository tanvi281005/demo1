package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class FoodOrder {
    private Integer serviceId;
    private Integer itemId;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String extras;
    private String instructions;

    // Getters & Setters
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getExtras() { return extras; }
    public void setExtras(String extras) { this.extras = extras; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
