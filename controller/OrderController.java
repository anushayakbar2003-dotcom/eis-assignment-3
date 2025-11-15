package com.automotive.eis.controller;

import com.automotive.eis.entity.Order;
import com.automotive.eis.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // POST /api/v1/orders/create - Order Management API
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String customer = (String) request.get("customer");
            String expectedDelivery = (String) request.get("expectedDelivery");
            
            Order order = new Order();
            order.setCustomerName(customer);
            if (expectedDelivery != null) {
                order.setExpectedDeliveryDate(LocalDate.parse(expectedDelivery));
            }
            order.setStatus("CREATED");
            
            Order saved = orderRepository.save(order);
            
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", saved.getOrderId());
            response.put("status", saved.getStatus());
            response.put("message", "Order created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating order: " + e.getMessage());
        }
    }

}

