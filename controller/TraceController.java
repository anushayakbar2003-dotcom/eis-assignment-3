package com.automotive.eis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/trace")
@CrossOrigin(origins = "*")
public class TraceController {

    // GET /api/trace/recall - Recall Traceability API
    @GetMapping("/recall")
    public ResponseEntity<?> traceRecall(
            @RequestParam String partId,
            @RequestParam(required = false) String lot) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("partId", partId);
        response.put("lotNumber", lot != null ? lot : "L-2025-A");
        response.put("shipments", Arrays.asList("SHIP567", "SHIP569"));
        response.put("customers", Arrays.asList("AutoWorld Ltd", "TurboMotors Inc"));
        response.put("status", "Recall traced successfully");
        return ResponseEntity.ok(response);
    }
}

