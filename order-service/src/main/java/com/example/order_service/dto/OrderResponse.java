package com.example.order_service.dto;

import com.example.order_service.entity.Order;

public class OrderResponse {
    private Order order;
    private UserDTO user;
    private CabDTO cab;

    // Getters and Setters
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public CabDTO getCab() { return cab; }
    public void setCab(CabDTO cab) { this.cab = cab; }
}
