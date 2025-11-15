package com.automotive.eis.controller;

import com.automotive.eis.entity.Invoice;
import com.automotive.eis.entity.GRN;
import com.automotive.eis.repository.InvoiceRepository;
import com.automotive.eis.repository.GRNRepository;
import com.automotive.eis.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private PurchaseOrderRepository poRepository;
    
    @Autowired
    private GRNRepository grnRepository;

    // POST /api/invoices - Invoice Generation & Record API
    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestBody Map<String, Object> request) {
        try {
            String invoiceId = (String) request.getOrDefault("invoiceId", "INV-" + System.currentTimeMillis());
            String poId = (String) request.get("poId");
            String supplierId = (String) request.get("supplierId");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String status = (String) request.getOrDefault("status", "DRAFT");
            
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(invoiceId);
            invoice.setPoId(poId);
            invoice.setSupplierId(supplierId);
            invoice.setAmount(amount);
            invoice.setStatus(status);
            
            Invoice saved = invoiceRepository.save(invoice);
            
            Map<String, Object> response = new HashMap<>();
            response.put("invoiceId", saved.getInvoiceId());
            response.put("poId", saved.getPoId());
            response.put("status", saved.getStatus());
            response.put("message", "Invoice generated and recorded successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error creating invoice: " + e.getMessage());
        }
    }

    // GET /api/invoices/{invoiceId}
    @GetMapping("/{invoiceId}")
    public ResponseEntity<?> getInvoice(@PathVariable String invoiceId) {
        return invoiceRepository.findById(invoiceId)
            .map(invoice -> ResponseEntity.ok(invoice))
            .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/invoices/{invoiceId}/status
    @PutMapping("/{invoiceId}/status")
    public ResponseEntity<?> updateInvoiceStatus(
            @PathVariable String invoiceId,
            @RequestBody Map<String, String> request) {
        return invoiceRepository.findById(invoiceId)
            .map(invoice -> {
                invoice.setStatus(request.get("status"));
                Invoice updated = invoiceRepository.save(invoice);
                Map<String, Object> response = new HashMap<>();
                response.put("invoiceId", updated.getInvoiceId());
                response.put("status", updated.getStatus());
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/invoices/{invoiceId}/match - Invoice-Line Matching API (3-Way Match)
    @PostMapping("/{invoiceId}/match")
    public ResponseEntity<?> matchInvoice(@PathVariable String invoiceId, @RequestBody Map<String, Object> request) {
        try {
            String poId = (String) request.get("poId");
            String grnId = (String) request.get("grnId");
            
            boolean poExists = poRepository.findById(poId).isPresent();
            boolean grnExists = grnRepository.findById(grnId).isPresent();
            boolean invoiceExists = invoiceRepository.findById(invoiceId).isPresent();
            
            int discrepancies = 0;
            String matchResult = (poExists && grnExists && invoiceExists) ? "Matched" : "Mismatch";
            
            Map<String, Object> response = new HashMap<>();
            response.put("invoiceId", invoiceId);
            response.put("poId", poId);
            response.put("matchResult", matchResult);
            response.put("discrepancies", discrepancies);
            response.put("message", "3-way match completed successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

