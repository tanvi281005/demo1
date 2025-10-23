package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Integer itemId;
    private Integer quantity;
    private String extras;
    private String instructions;
    private Boolean cutleryRequired;
    private Integer vendorId;
    private BigDecimal price;

    public OrderItemDTO() {}

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getExtras() { return extras; }
    public void setExtras(String extras) { this.extras = extras; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Boolean getCutleryRequired() { return cutleryRequired; }
    public void setCutleryRequired(Boolean cutleryRequired) { this.cutleryRequired = cutleryRequired; }

    public Integer getVendorId() { return vendorId; }
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
