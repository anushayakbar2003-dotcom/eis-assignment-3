package com.automotive.eis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    // POST /api/alerts/low-stock - Reorder Trigger / Alert API
    @PostMapping("/low-stock")
    public ResponseEntity<?> lowStockAlert(@RequestBody Map<String, Object> request) {
        try {
            String partId = (String) request.get("partId");
            Integer currentQty = ((Number) request.get("currentQty")).intValue();
            Integer threshold = ((Number) request.get("threshold")).intValue();
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Low stock alert triggered");
            response.put("partId", partId);
            response.put("recommendedSupplier", "AutoParts Co.");
            response.put("suggestedPO", "PO-" + System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

