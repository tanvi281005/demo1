package com.thecodealchemist.spring_boot_project.model;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private Integer studentId;
    private BigDecimal totalPrice;
    private Integer noOfItems;
    private Boolean cutleryRequired;
    private Integer vendorId;
    private Boolean isEmpty;
    private List<CartItemDTO> items;

    // Getters & Setters
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public Integer getNoOfItems() { return noOfItems; }
    public void setNoOfItems(Integer noOfItems) { this.noOfItems = noOfItems; }

    public Boolean getCutleryRequired() { return cutleryRequired; }
    public void setCutleryRequired(Boolean cutleryRequired) { this.cutleryRequired = cutleryRequired; }

    public Integer getVendorId() { return vendorId; }
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; }

    public Boolean getIsEmpty() { return isEmpty; }
    public void setIsEmpty(Boolean isEmpty) { this.isEmpty = isEmpty; }

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
}
