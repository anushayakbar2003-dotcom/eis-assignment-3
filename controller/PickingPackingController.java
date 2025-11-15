package com.automotive.eis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PickingPackingController {

    // POST /api/v1/picklists/create - Pick List & Task API
    @PostMapping("/picklists/create")
    public ResponseEntity<?> createPickList(@RequestBody Map<String, Object> request) {
        try {
            Integer orderId = ((Number) request.get("orderId")).intValue();
            
            Map<String, Object> response = new HashMap<>();
            response.put("pickListId", 3000 + orderId);
            response.put("status", "created");
            response.put("message", "Pick list generated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // POST /api/v1/packing/label - Packing & Labeling API
    @PostMapping("/packing/label")
    public ResponseEntity<?> createPackingLabels(@RequestBody Map<String, Object> request) {
        try {
            Integer orderId = ((Number) request.get("orderId")).intValue();
            
            List<Map<String, Object>> labels = new ArrayList<>();
            List<Map<String, Object>> boxes = (List<Map<String, Object>>) request.get("boxes");
            if (boxes != null) {
                for (Map<String, Object> box : boxes) {
                    Map<String, Object> label = new HashMap<>();
                    label.put("labelId", "LBL-" + System.currentTimeMillis());
                    label.put("carrier", "FedEx");
                    label.put("trackingNo", "FE" + (100000 + new Random().nextInt(900000)));
                    labels.add(label);
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", orderId);
            response.put("packingSlip", "PKSLP-" + orderId);
            response.put("labels", labels);
            response.put("message", "Packing and labeling completed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

