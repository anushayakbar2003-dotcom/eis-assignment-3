package com.automotive.eis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CreditRefundController {

    // POST /api/credit - Credit / Refund Processing API
    @PostMapping("/credit")
    public ResponseEntity<?> processCredit(@RequestBody Map<String, Object> request) {
        try {
            String invoiceId = (String) request.get("invoiceId");
            Double amount = ((Number) request.get("amount")).doubleValue();
            String reason = (String) request.get("reason");
            
            Map<String, Object> response = new HashMap<>();
            response.put("creditNoteId", "CRD-" + System.currentTimeMillis());
            response.put("invoiceId", invoiceId);
            response.put("amount", amount);
            response.put("status", "Processed");
            response.put("message", "Credit successfully issued");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // POST /api/refund
    @PostMapping("/refund")
    public ResponseEntity<?> processRefund(@RequestBody Map<String, Object> request) {
        try {
            String invoiceId = (String) request.get("invoiceId");
            Double amount = ((Number) request.get("amount")).doubleValue();
            
            Map<String, Object> response = new HashMap<>();
            response.put("creditNoteId", "CRD-" + System.currentTimeMillis());
            response.put("invoiceId", invoiceId);
            response.put("amount", amount);
            response.put("status", "Processed");
            response.put("message", "Refund successfully issued");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

