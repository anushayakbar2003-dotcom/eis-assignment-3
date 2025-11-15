package com.automotive.eis.controller;

import com.automotive.eis.entity.Inventory;
import com.automotive.eis.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/replenish")
@CrossOrigin(origins = "*")
public class ReplenishmentController {

    @Autowired
    private InventoryRepository inventoryRepository;

    // GET /api/replenish/{partId}?policy=EOQ - Replenishment Planner API
    @GetMapping("/{partId}")
    public ResponseEntity<?> getReplenishmentPlan(
            @PathVariable String partId,
            @RequestParam(required = false, defaultValue = "EOQ") String policy) {
        
        Optional<Inventory> inv = inventoryRepository.findByPartId(partId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("partId", partId);
        response.put("policy", policy);
        
        if (inv.isPresent()) {
            Inventory inventory = inv.get();
            response.put("reorderPoint", inventory.getReorderPoint() > 0 ? inventory.getReorderPoint() : 150);
            response.put("recommendedOrderQty", 600);
            response.put("safetyStock", inventory.getSafetyStock() > 0 ? inventory.getSafetyStock() : 100);
        } else {
            response.put("reorderPoint", 150);
            response.put("recommendedOrderQty", 600);
            response.put("safetyStock", 100);
        }
        
        return ResponseEntity.ok(response);
    }

}

