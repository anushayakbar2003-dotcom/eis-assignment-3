package com.automotive.eis.controller;

import com.automotive.eis.entity.GRN;
import com.automotive.eis.entity.Inventory;
import com.automotive.eis.repository.GRNRepository;
import com.automotive.eis.repository.InventoryRepository;
import com.automotive.eis.repository.PurchaseOrderRepository;
import com.automotive.eis.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/receipts")
@CrossOrigin(origins = "*")
public class GoodsReceiptController {

    @Autowired
    private GRNRepository grnRepository;
    
    @Autowired
    private PurchaseOrderRepository poRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;

    // POST /api/v1/receipts - Goods Receipt API
    @PostMapping
    public ResponseEntity<?> createGoodsReceipt(@RequestBody Map<String, Object> request) {
        try {
            String grnId = (String) request.getOrDefault("grnId", "GRN-" + System.currentTimeMillis());
            String asnId = (String) request.get("asnId");
            String poId = (String) request.get("poId");
            
            List<Map<String, Object>> lines = (List<Map<String, Object>>) request.get("lines");
            if (lines == null || lines.isEmpty()) {
                return ResponseEntity.badRequest().body("Lines are required");
            }
            
            // Create GRN
            Map<String, Object> firstLine = lines.get(0);
            Integer receivedQty = ((Number) firstLine.get("receivedQty")).intValue();
            
            GRN grn = new GRN();
            grn.setGrnId(grnId);
            grn.setPoId(poId);
            grn.setAsnId(asnId);
            grn.setReceivedQty(receivedQty);
            grn.setInspectedQty(receivedQty);
            grn.setInspector((String) request.get("inspector"));
            grn.setStatus("POSTED");
            
            GRN savedGRN = grnRepository.save(grn);
            
            // Update inventory
            List<Map<String, Object>> updatedInventory = new ArrayList<>();
            for (Map<String, Object> line : lines) {
                String partId = (String) line.get("partId");
                Integer qty = ((Number) line.get("receivedQty")).intValue();
                
                Optional<Inventory> invOpt = inventoryRepository.findByPartId(partId);
                Inventory inv;
                if (invOpt.isPresent()) {
                    inv = invOpt.get();
                    inv.setOnHand(inv.getOnHand() + qty);
                    inv.setAvailable(inv.getOnHand() - inv.getReserved());
                } else {
                    inv = new Inventory(partId, qty);
                }
                inventoryRepository.save(inv);
                
                Map<String, Object> invUpdate = new HashMap<>();
                invUpdate.put("partId", partId);
                invUpdate.put("onHand", inv.getOnHand());
                invUpdate.put("lot", line.get("lot"));
                updatedInventory.add(invUpdate);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("grnId", savedGRN.getGrnId());
            response.put("status", savedGRN.getStatus());
            response.put("updatedInventory", updatedInventory);
            response.put("message", "Goods receipt posted and inventory updated");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating goods receipt: " + e.getMessage());
        }
    }
}

