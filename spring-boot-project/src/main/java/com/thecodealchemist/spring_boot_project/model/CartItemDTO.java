package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class CartItemDTO {
    private Integer itemId;
    private String itemName;
    private BigDecimal price;
    private Integer quantity;
    private String extras;
    private String instructions;
    private String photo;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getExtras() { return extras; }
    public void setExtras(String extras) { this.extras = extras; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getPhoto() {
    return photo;
}

public void setPhoto(String photo) {
    this.photo = photo;
}
}
