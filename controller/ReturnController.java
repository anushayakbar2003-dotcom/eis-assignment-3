package com.automotive.eis.controller;

import com.automotive.eis.entity.Return;
import com.automotive.eis.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/returns")
@CrossOrigin(origins = "*")
public class ReturnController {

    @Autowired
    private ReturnRepository returnRepository;

    // POST /api/v1/returns/authorize - Return Authorization (RMA) API
    @PostMapping("/authorize")
    public ResponseEntity<?> authorizeReturn(@RequestBody Map<String, Object> request) {
        try {
            String rmaId = "RMA-" + System.currentTimeMillis();
            Integer customerId = ((Number) request.get("customerId")).intValue();
            String part = (String) request.get("part");
            Integer qty = ((Number) request.get("qty")).intValue();
            
            Return returnOrder = new Return();
            returnOrder.setReturnId("RET-" + System.currentTimeMillis());
            returnOrder.setRmaId(rmaId);
            returnOrder.setCustomerId(customerId);
            returnOrder.setPartId(part);
            returnOrder.setQuantity(qty);
            returnOrder.setReason((String) request.get("reason"));
            returnOrder.setStatus("PENDING");
            
            returnRepository.save(returnOrder);
            
            Map<String, Object> response = new HashMap<>();
            response.put("rmaId", rmaId);
            response.put("status", "pending");
            response.put("labelUrl", "https://eis.com/labels/" + rmaId + ".pdf");
            response.put("message", "Return authorization created");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error authorizing return: " + e.getMessage());
        }
    }

    // POST /api/v1/returns/disposition - Return Receipt & Disposition API
    @PostMapping("/disposition")
    public ResponseEntity<?> processDisposition(@RequestBody Map<String, Object> request) {
        try {
            String partId = (String) request.get("partId");
            Integer qty = ((Number) request.get("qty")).intValue();
            String disposition = (String) request.get("disposition");
            
            Map<String, Object> response = new HashMap<>();
            response.put("returnId", "RET-" + System.currentTimeMillis());
            response.put("status", "processed");
            response.put("message", "Returned items " + disposition + " successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

