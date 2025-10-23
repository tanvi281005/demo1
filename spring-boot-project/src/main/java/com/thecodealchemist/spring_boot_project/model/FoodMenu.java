package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class FoodMenu {
    private Integer itemId;
    private Integer vendorId;
    private String itemName;
    private String description;
    private String photo; // ✅ new field
    private BigDecimal price;
    private String culture; // Punjabi, Rajasthani, etc.
    private String reviews; // JSON as String

    // Getters & Setters
    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public Integer getVendorId() { return vendorId; }
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCulture() { return culture; }
    public void setCulture(String culture) { this.culture = culture; }

    public String getReviews() { return reviews; }
    public void setReviews(String reviews) { this.reviews = reviews; }

    public String getPhoto() {
    return photo;
}

public void setPhoto(String photo) {
    this.photo = photo;
}
}
