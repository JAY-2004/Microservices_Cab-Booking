package com.example.order_service.controller;

import com.example.order_service.dto.CabDTO;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.UserDTO;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders_final")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setStatus("Pending");
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // Fetch full order info with user + cab details
    @GetMapping("/full/{id}")
    public OrderResponse getFullOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;

        UserDTO user = restTemplate.getForObject(
                "http://user-service/users/" + order.getUserId(), UserDTO.class);
        CabDTO cab = restTemplate.getForObject(
                "http://cab-service/cabs/" + order.getCabId(), CabDTO.class);

        OrderResponse response = new OrderResponse();
        response.setOrder(order);
        response.setUser(user);
        response.setCab(cab);
        return response;
    }

    // Full update of order
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setUserId(orderDetails.getUserId());
            order.setCabId(orderDetails.getCabId());
            order.setPickupLocation(orderDetails.getPickupLocation());
            order.setDropLocation(orderDetails.getDropLocation());
            order.setStatus(orderDetails.getStatus());
            return orderRepository.save(order);
        }
        return null;
    }

    // Partial update – status only
    @PutMapping("/status/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdate statusUpdate) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(statusUpdate.getStatus());
            return orderRepository.save(order);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }

    // DTO for updating order status
    public static class OrderStatusUpdate {
        private String status;

        public OrderStatusUpdate() {}

        public OrderStatusUpdate(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}