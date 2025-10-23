package com.thecodealchemist.spring_boot_project.model;

import java.util.List;

public class OrdersRequest {
    private List<OrderItemDTO> orders;

    public OrdersRequest() {}

    public List<OrderItemDTO> getOrders() { return orders; }
    public void setOrders(List<OrderItemDTO> orders) { this.orders = orders; }
}
