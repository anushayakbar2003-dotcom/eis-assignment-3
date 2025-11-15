package com.automotive.eis.controller;

import com.automotive.eis.entity.Inventory;
import com.automotive.eis.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // GET /api/v1/inventory/{partId} - Inventory Query API
    @GetMapping("/{partId}")
    public ResponseEntity<?> getInventory(@PathVariable String partId) {
        Optional<Inventory> inventory = inventoryService.getInventoryByPartId(partId);
        if (inventory.isPresent()) {
            Inventory inv = inventory.get();
            Map<String, Object> response = new HashMap<>();
            response.put("partId", inv.getPartId());
            response.put("onHand", inv.getOnHand());
            response.put("reserved", inv.getReserved());
            response.put("available", inv.getAvailable());
            response.put("lastUpdated", inv.getLastUpdated());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/v1/inventory/adjust - Inventory Adjustment API
    @PostMapping("/adjust")
    public ResponseEntity<?> adjustInventory(@RequestBody Map<String, Object> request) {
        try {
            String adjustmentId = (String) request.get("adjustmentId");
            String partId = (String) request.get("partId");
            Integer delta = ((Number) request.get("delta")).intValue();
            String reason = (String) request.get("reason");
            String lotNumber = (String) request.get("lot");

            Optional<Inventory> existing = inventoryService.getInventoryByPartId(partId);
            Integer previousOnHand = existing.isPresent() ? existing.get().getOnHand() : 0;

            Inventory updated = inventoryService.adjustInventory(partId, delta, lotNumber);
            if (updated != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("adjustmentId", adjustmentId);
                response.put("partId", partId);
                response.put("previousOnHand", previousOnHand);
                response.put("newOnHand", updated.getOnHand());
                response.put("lot", lotNumber);
                response.put("status", "POSTED");
                response.put("message", "Inventory adjusted and audit log recorded");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to adjust inventory");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error adjusting inventory: " + e.getMessage());
        }
    }
}

