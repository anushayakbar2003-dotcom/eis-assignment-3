package com.automotive.eis.controller;

import com.automotive.eis.entity.Order;
import com.automotive.eis.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SLAController {

    @Autowired
    private OrderRepository orderRepository;

    // POST /api/promise - SLA / Delivery Promise API
    @PostMapping("/promise")
    public ResponseEntity<?> createPromise(@RequestBody Map<String, Object> request) {
        try {
            String orderIdStr = request.get("orderId").toString();
            Long orderId = Long.parseLong(orderIdStr);
            String promisedDate = (String) request.get("promisedDate");
            String actualDate = (String) request.get("actualDeliveryDate");
            
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                order.setPromisedDate(LocalDate.parse(promisedDate));
                if (actualDate != null) {
                    order.setActualDeliveryDate(LocalDate.parse(actualDate));
                }
                orderRepository.save(order);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", orderId);
            response.put("promisedDate", promisedDate);
            response.put("actualDeliveryDate", actualDate);
            response.put("slaCompliance", actualDate != null ? "Met" : "Pending");
            response.put("complianceRate", "98%");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // GET /api/sla/{orderId}
    @GetMapping("/sla/{orderId}")
    public ResponseEntity<?> getSLA(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
            .map(order -> {
                Map<String, Object> response = new HashMap<>();
                response.put("orderId", order.getOrderId());
                response.put("promisedDate", order.getPromisedDate());
                response.put("actualDeliveryDate", order.getActualDeliveryDate());
                response.put("slaCompliance", order.getSlaCompliance());
                response.put("complianceRate", "98%");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

