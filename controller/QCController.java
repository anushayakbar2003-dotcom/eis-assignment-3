package com.automotive.eis.controller;

import com.automotive.eis.entity.QualityInspection;
import com.automotive.eis.repository.QualityInspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/qc")
@CrossOrigin(origins = "*")
public class QCController {

    @Autowired
    private QualityInspectionRepository qcRepository;

    // POST /api/v1/qc/inspection - Quality Inspection / QC Result API
    @PostMapping("/inspection")
    public ResponseEntity<?> submitQCInspection(@RequestBody Map<String, Object> request) {
        try {
            String inspectionId = (String) request.getOrDefault("inspectionId", "QC-" + System.currentTimeMillis());
            String grnId = (String) request.get("grnId");
            String partId = (String) request.get("partId");
            String result = (String) request.get("result");
            
            QualityInspection qc = new QualityInspection();
            qc.setInspectionId(inspectionId);
            qc.setGrnId(grnId);
            qc.setPartId(partId);
            qc.setLotNumber((String) request.get("lot"));
            qc.setInspector((String) request.get("inspector"));
            qc.setResult(result);
            qc.setDefectCode((String) request.get("defectCode"));
            qc.setSampleSize(((Number) request.getOrDefault("sampleSize", 10)).intValue());
            qc.setFailedQty(((Number) request.getOrDefault("failedQty", 0)).intValue());
            qc.setNotes((String) request.get("notes"));
            
            QualityInspection saved = qcRepository.save(qc);
            
            Map<String, Object> response = new HashMap<>();
            response.put("inspectionId", saved.getInspectionId());
            response.put("partId", saved.getPartId());
            response.put("lot", saved.getLotNumber());
            response.put("result", saved.getResult());
            
            List<Map<String, Object>> actions = new ArrayList<>();
            if ("FAIL".equals(result)) {
                Map<String, Object> action1 = new HashMap<>();
                action1.put("action", "QUARANTINE");
                action1.put("qty", saved.getFailedQty());
                actions.add(action1);
                
                Map<String, Object> action2 = new HashMap<>();
                action2.put("action", "RETURN_TO_SUPPLIER");
                action2.put("qty", saved.getFailedQty());
                action2.put("rmaId", "RMA-" + System.currentTimeMillis());
                actions.add(action2);
            }
            response.put("actions", actions);
            response.put("message", "QC " + result.toLowerCase() + " recorded");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error submitting QC inspection: " + e.getMessage());
        }
    }

    // GET /api/v1/qc/plan/{partId} - QC Sampling & Workflow API
    @GetMapping("/plan/{partId}")
    public ResponseEntity<?> getQCSamplingPlan(@PathVariable String partId) {
        Map<String, Object> response = new HashMap<>();
        response.put("partId", partId);
        response.put("aql", "1.5");
        response.put("inspectionLevel", "II");
        response.put("sampleSize", 10);
        
        Map<String, Object> acceptanceCriteria = new HashMap<>();
        acceptanceCriteria.put("maxDefects", 1);
        response.put("acceptanceCriteria", acceptanceCriteria);
        
        response.put("workflow", Arrays.asList("RECEIPT", "SAMPLE_INSPECTION", "FULL_INSPECTION_IF_FAIL"));
        response.put("lastUpdated", java.time.LocalDateTime.now().toString());
        
        return ResponseEntity.ok(response);
    }
}

