package com.automotive.eis.controller;

import com.automotive.eis.entity.AuditLog;
import com.automotive.eis.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "*")
public class AuditController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // POST /api/audit/event - Audit & Event Log API
    @PostMapping("/event")
    public ResponseEntity<?> logEvent(@RequestBody Map<String, Object> request) {
        try {
            String entityType = (String) request.get("entity");
            String entityId = (String) request.get("entityId");
            String eventType = (String) request.get("eventType");
            String userId = (String) request.getOrDefault("user_id", "system");
            
            AuditLog auditLog = new AuditLog();
            auditLog.setEntityType(entityType);
            auditLog.setEntityId(entityId);
            auditLog.setEventType(eventType);
            auditLog.setUserId(userId);
            auditLog.setDetails((String) request.getOrDefault("details", ""));
            
            AuditLog saved = auditLogRepository.save(auditLog);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Event logged successfully");
            response.put("entity", entityType);
            response.put("entityId", entityId);
            response.put("eventType", eventType);
            response.put("timestamp", saved.getTimestamp());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // GET /api/audit - Get audit logs
    @GetMapping
    public ResponseEntity<?> getAuditLogs(
            @RequestParam(required = false) String entity,
            @RequestParam(required = false) String id) {
        
        List<AuditLog> logs;
        if (entity != null && id != null) {
            logs = auditLogRepository.findByEntityTypeAndEntityId(entity, id);
        } else if (entity != null) {
            logs = auditLogRepository.findByEntityType(entity);
        } else {
            logs = auditLogRepository.findAll();
        }
        
        return ResponseEntity.ok(logs);
    }
}

