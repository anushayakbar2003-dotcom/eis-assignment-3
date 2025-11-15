package com.automotive.eis.controller;

import com.automotive.eis.entity.ASN;
import com.automotive.eis.repository.ASNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/asn")
@CrossOrigin(origins = "*")
public class ASNController {

    @Autowired
    private ASNRepository asnRepository;

    // POST /api/v1/asn - ASN (Advance Shipment Notice) API
    @PostMapping
    public ResponseEntity<?> createASN(@RequestBody Map<String, Object> request) {
        try {
            String asnId = (String) request.getOrDefault("asnId", "ASN-" + System.currentTimeMillis());
            String poId = (String) request.get("poId");
            String supplierId = (String) request.get("supplierId");
            String etaStr = (String) request.get("eta");
            
            ASN asn = new ASN();
            asn.setAsnId(asnId);
            asn.setPoId(poId);
            asn.setSupplierId(supplierId);
            if (etaStr != null) {
                asn.setEta(LocalDateTime.parse(etaStr));
            }
            asn.setCarrier((String) request.get("carrier"));
            asn.setStatus("RECEIVED");
            
            ASN saved = asnRepository.save(asn);
            
            Map<String, Object> response = new HashMap<>();
            response.put("asnId", saved.getAsnId());
            response.put("status", saved.getStatus());
            response.put("poId", saved.getPoId());
            response.put("expectedArrival", saved.getEta());
            response.put("matchedLines", request.get("lines") != null ? ((List<?>) request.get("lines")).size() : 0);
            response.put("message", "ASN accepted and scheduled for receiving");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating ASN: " + e.getMessage());
        }
    }
}

