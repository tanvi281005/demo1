package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class MarketTransaction {

    private Integer serviceId;
    private Integer itemId;  
    private Boolean isApproved;
    private BigDecimal negotiatedPrice;
    private BigDecimal finalPrice;
    private BigDecimal originalPrice;

    // New fields for frontend display
    private String title;       // item title
    private String buyerName;   // buyer's name for seller view
    private String sellerName;  // seller's name for buyer view

    public MarketTransaction() {}

    public MarketTransaction(Integer serviceId, Integer itemId, Boolean isApproved,
                             BigDecimal negotiatedPrice, BigDecimal finalPrice, BigDecimal originalPrice) {
        this.serviceId = serviceId;
        this.itemId = itemId;
        this.isApproved = isApproved;
        this.negotiatedPrice = negotiatedPrice;
        this.finalPrice = finalPrice;
        this.originalPrice = originalPrice;
    }

    // Getters & Setters
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    public BigDecimal getNegotiatedPrice() { return negotiatedPrice; }
    public void setNegotiatedPrice(BigDecimal negotiatedPrice) { this.negotiatedPrice = negotiatedPrice; }

    public BigDecimal getFinalPrice() { return finalPrice; }
    public void setFinalPrice(BigDecimal finalPrice) { this.finalPrice = finalPrice; }

    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
}
