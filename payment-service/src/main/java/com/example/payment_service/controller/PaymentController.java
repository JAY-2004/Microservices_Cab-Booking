package com.example.payment_service.controller;

import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/payments_final")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        payment.setStatus("Completed");

        // Update order status to "Paid" using Eureka-based URL
        restTemplate.put("http://order-service/orders_final/status/" + payment.getOrderId(),
                new OrderStatusUpdate("Paid"), Void.class);

        return paymentRepository.save(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentRepository.deleteById(id);
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
