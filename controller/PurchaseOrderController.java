package com.automotive.eis.controller;

import com.automotive.eis.entity.PurchaseOrder;
import com.automotive.eis.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService poService;

    // POST /api/purchase-orders - Purchase Order Lifecycle API (Create)
    @PostMapping
    public ResponseEntity<?> createPurchaseOrder(@RequestBody Map<String, Object> request) {
        try {
            String poId = (String) request.get("poId");
            String supplierId = (String) request.get("supplierId");
            String partId = (String) request.get("partId");
            Integer quantity = ((Number) request.get("quantity")).intValue();
            String status = (String) request.getOrDefault("status", "CREATED");

            PurchaseOrder po = new PurchaseOrder(poId, supplierId, partId, quantity);
            po.setStatus(status);
            
            PurchaseOrder created = poService.createPurchaseOrder(po);
            
            Map<String, Object> response = new HashMap<>();
            response.put("poId", created.getPoId());
            response.put("status", created.getStatus());
            response.put("message", "Purchase order created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating purchase order: " + e.getMessage());
        }
    }

    // GET /api/purchase-orders/{poId} - Get Purchase Order
    @GetMapping("/{poId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable String poId) {
        Optional<PurchaseOrder> po = poService.getPurchaseOrderById(poId);
        if (po.isPresent()) {
            return ResponseEntity.ok(po.get());
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /api/purchase-orders/{poId}/status - Update PO Status
    @PutMapping("/{poId}/status")
    public ResponseEntity<?> updatePOStatus(
            @PathVariable String poId,
            @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            PurchaseOrder updated = poService.updatePOStatus(poId, status);
            if (updated != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("poId", updated.getPoId());
                response.put("status", updated.getStatus());
                response.put("message", "Purchase order status updated successfully");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error updating PO status: " + e.getMessage());
        }
    }

    // POST /api/purchase-orders/{poId}/match-receipt - PO Matching & GRN API
    @PostMapping("/{poId}/match-receipt")
    public ResponseEntity<?> matchReceipt(
            @PathVariable String poId,
            @RequestBody Map<String, Object> request) {
        try {
            Integer receivedQty = ((Number) request.get("receivedQty")).intValue();
            Integer inspectedQty = ((Number) request.get("inspectedQty")).intValue();

            // This would call GRNService to create GRN and match
            // For now, returning a simulated response
            Map<String, Object> response = new HashMap<>();
            response.put("poId", poId);
            response.put("matchedQty", inspectedQty);
            response.put("discrepancies", receivedQty - inspectedQty);
            response.put("grnGenerated", true);
            response.put("grnId", "GRN-" + System.currentTimeMillis());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error matching receipt: " + e.getMessage());
        }
    }
}

