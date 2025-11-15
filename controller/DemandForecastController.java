package com.automotive.eis.controller;

import com.automotive.eis.entity.DemandForecast;
import com.automotive.eis.repository.DemandForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/forecast")
@CrossOrigin(origins = "*")
public class DemandForecastController {

    @Autowired
    private DemandForecastRepository forecastRepository;

    // POST /api/forecast - Demand Forecasting ML API
    @PostMapping
    public ResponseEntity<?> createForecast(@RequestBody Map<String, Object> request) {
        try {
            String partId = (String) request.get("partId");
            
            // Simulate ML forecasting (in real system, this would use actual ML model)
            int forecastUnits = new Random().nextInt(500) + 100;
            String confidenceLevel = "92%";
            
            DemandForecast forecast = new DemandForecast();
            forecast.setPartId(partId);
            forecast.setForecastUnits(forecastUnits);
            forecast.setConfidenceLevel(confidenceLevel);
            forecast.setForecastDate(LocalDate.now().plusMonths(1));
            
            DemandForecast saved = forecastRepository.save(forecast);
            
            Map<String, Object> response = new HashMap<>();
            response.put("partId", saved.getPartId());
            response.put("forecastUnits", saved.getForecastUnits());
            response.put("confidenceLevel", saved.getConfidenceLevel());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating forecast: " + e.getMessage());
        }
    }

    // GET /api/forecast/{partId} - Get Forecast
    @GetMapping("/{partId}")
    public ResponseEntity<?> getForecast(@PathVariable String partId) {
        Optional<DemandForecast> forecast = forecastRepository.findFirstByPartIdOrderByCreatedAtDesc(partId);
        if (forecast.isPresent()) {
            DemandForecast f = forecast.get();
            Map<String, Object> response = new HashMap<>();
            response.put("partId", f.getPartId());
            response.put("forecastUnits", f.getForecastUnits());
            response.put("confidenceLevel", f.getConfidenceLevel());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/forecast/{partId}/explain - Forecast Explanation API
    @GetMapping("/{partId}/explain")
    public ResponseEntity<?> explainForecast(@PathVariable String partId) {
        Map<String, Object> response = new HashMap<>();
        response.put("partId", partId);
        response.put("keyDrivers", Arrays.asList("Seasonal demand increase", "Promotional offer impact"));
        response.put("confidenceScore", "High");
        return ResponseEntity.ok(response);
    }
}

