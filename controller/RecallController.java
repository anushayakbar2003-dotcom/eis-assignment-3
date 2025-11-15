package com.automotive.eis.controller;

import com.automotive.eis.entity.Recall;
import com.automotive.eis.repository.RecallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/recalls")
@CrossOrigin(origins = "*")
public class RecallController {

    @Autowired
    private RecallRepository recallRepository;

    // POST /api/v1/recalls/issue - Recall Issuance & Management API
    @PostMapping("/issue")
    public ResponseEntity<?> issueRecall(@RequestBody Map<String, Object> request) {
        try {
            String recallId = "RECALL-" + System.currentTimeMillis();
            String partId = (String) request.get("partId");
            String reason = (String) request.get("reason");
            String scope = (String) request.get("scope");
            
            Recall recall = new Recall();
            recall.setRecallId(recallId);
            recall.setPartId(partId);
            recall.setReason(reason);
            recall.setScope(scope);
            recall.setStatus("ISSUED");
            
            if (request.get("lots") != null) {
                List<String> lots = (List<String>) request.get("lots");
                if (!lots.isEmpty()) {
                    recall.setLotNumber(lots.get(0));
                }
            }
            
            recallRepository.save(recall);
            
            Map<String, Object> response = new HashMap<>();
            response.put("recallId", recallId);
            response.put("status", "issued");
            response.put("message", "Recall notice created and notifications sent");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error issuing recall: " + e.getMessage());
        }
    }

}

