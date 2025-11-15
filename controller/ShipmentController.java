package com.automotive.eis.controller;

import com.automotive.eis.entity.Shipment;
import com.automotive.eis.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shipments")
@CrossOrigin(origins = "*")
public class ShipmentController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // POST /api/v1/shipments - Shipment Scheduling / Carrier Booking API
    @PostMapping
    public ResponseEntity<?> scheduleShipment(@RequestBody Map<String, Object> request) {
        try {
            String shipmentId = "SHP-" + System.currentTimeMillis();
            String partId = (String) request.get("partId");
            Integer qty = ((Number) request.get("qty")).intValue();
            String destination = (String) request.get("destination");
            String carrier = (String) request.get("carrier");
            String shipDate = (String) request.getOrDefault("shipDate", LocalDate.now().toString());
            
            Shipment shipment = new Shipment();
            shipment.setShipmentId(shipmentId);
            shipment.setPartId(partId);
            shipment.setQuantity(qty);
            shipment.setDestination(destination);
            shipment.setCarrier(carrier);
            shipment.setShipDate(LocalDate.parse(shipDate));
            shipment.setEta(LocalDateTime.now().plusDays(3));
            shipment.setStatus("SCHEDULED");
            
            Shipment saved = shipmentRepository.save(shipment);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", saved.getShipmentId());
            response.put("partId", saved.getPartId());
            response.put("qty", saved.getQuantity());
            response.put("destination", saved.getDestination());
            response.put("carrier", saved.getCarrier());
            response.put("shipDate", saved.getShipDate().toString());
            response.put("eta", saved.getEta());
            response.put("status", saved.getStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error scheduling shipment: " + e.getMessage());
        }
    }

    // POST /api/v1/shipments/pod - Shipment Tracking / Proof-of-Delivery API
    @PostMapping("/pod")
    public ResponseEntity<?> updatePOD(@RequestBody Map<String, Object> request) {
        try {
            String shipmentId = (String) request.get("shipmentId");
            String deliveredBy = (String) request.get("deliveredBy");
            String deliveryTime = (String) request.get("deliveryTime");
            
            Optional<Shipment> shipmentOpt = shipmentRepository.findById(shipmentId);
            if (shipmentOpt.isPresent()) {
                Shipment shipment = shipmentOpt.get();
                shipment.setDeliveredBy(deliveredBy);
                shipment.setDeliveryTime(LocalDateTime.parse(deliveryTime));
                shipment.setStatus("DELIVERED");
                shipmentRepository.save(shipment);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("shipmentId", shipmentId);
            response.put("status", "Delivered");
            response.put("message", "Proof of delivery uploaded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

