package com.automotive.eis.controller;

import com.automotive.eis.entity.Order;
import com.automotive.eis.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/fulfillment")
@CrossOrigin(origins = "*")
public class FulfillmentController {

    @Autowired
    private OrderRepository orderRepository;

    // POST /api/v1/fulfillment/confirm - Fulfillment Confirmation API
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmFulfillment(@RequestBody Map<String, Object> request) {
        try {
            Long orderId = ((Number) request.get("orderId")).longValue();
            String event = (String) request.get("event");
            
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                order.setStatus(event.toUpperCase());
                orderRepository.save(order);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", orderId);
            response.put("event", event);
            response.put("message", "Fulfillment event confirmed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

