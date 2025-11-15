package com.automotive.eis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CarrierRatesController {

    // GET /api/v1/rates - Carrier Rates & Routing API
    @GetMapping("/rates")
    public ResponseEntity<?> getCarrierRates(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false, defaultValue = "0") Double weightKg) {
        
        Map<String, Object> response = new HashMap<>();
        Map<String, String> route = new HashMap<>();
        route.put("from", from);
        route.put("to", to);
        response.put("route", route);
        
        List<Map<String, Object>> options = new ArrayList<>();
        
        Map<String, Object> option1 = new HashMap<>();
        option1.put("carrier", "MoverX");
        option1.put("service", "Road Express");
        option1.put("transitDays", 3);
        option1.put("price", 145.50);
        option1.put("currency", "USD");
        option1.put("estimatedPickup", LocalDateTime.now().plusHours(8).toString());
        options.add(option1);
        
        Map<String, Object> option2 = new HashMap<>();
        option2.put("carrier", "FastShip");
        option2.put("service", "Priority Air");
        option2.put("transitDays", 1);
        option2.put("price", 420.00);
        option2.put("currency", "USD");
        option2.put("estimatedPickup", LocalDateTime.now().plusHours(14).toString());
        options.add(option2);
        
        response.put("options", options);
        return ResponseEntity.ok(response);
    }
}

