package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;

public class TransactionViewDTO {
    private Integer serviceId;
    private Integer itemId;
    private Boolean isApproved;
    private BigDecimal negotiatedPrice;
    private BigDecimal finalPrice;
    private BigDecimal originalPrice;

    // Joined fields for UI
    private String title;
    private String buyerName;
    private String sellerName;

    public TransactionViewDTO() {}

    public TransactionViewDTO(Integer serviceId, Integer itemId,
                              BigDecimal negotiatedPrice, BigDecimal finalPrice, BigDecimal originalPrice,Boolean isApproved, 
                              String title, String buyerName, String sellerName) {
        this.serviceId = serviceId;
        this.itemId = itemId;
        this.negotiatedPrice = negotiatedPrice;
        this.finalPrice = finalPrice;
        this.originalPrice = originalPrice;
        this.isApproved = isApproved;
        this.title = title;
        this.buyerName = buyerName;
        this.sellerName = sellerName;
    }

    // getters / setters (generate with IDE)
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
