package com.automotive.eis.controller;

import com.automotive.eis.dto.SupplierRequest;
import com.automotive.eis.dto.SupplierResponse;
import com.automotive.eis.dto.PerformanceRequest;
import com.automotive.eis.entity.Supplier;
import com.automotive.eis.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // POST /api/suppliers - Supplier Registry API
    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest request) {
        try {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(request.getSupplierId());
            supplier.setName(request.getName());
            supplier.setContact(request.getContact());
            supplier.setLeadTimeDays(request.getLeadTimeDays());
            supplier.setCertifications(request.getCertifications());

            Supplier created = supplierService.createSupplier(supplier);
            SupplierResponse response = new SupplierResponse(
                "Supplier record created successfully",
                created.getSupplierId(),
                created.getStatus()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            SupplierResponse errorResponse = new SupplierResponse(
                "Error creating supplier: " + e.getMessage(),
                null,
                "error"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET /api/suppliers - Get all suppliers
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    // GET /api/suppliers/{supplierId} - Get supplier by ID
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable String supplierId) {
        Optional<Supplier> supplier = supplierService.getSupplierById(supplierId);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get());
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /api/suppliers/{supplierId} - Update supplier
    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable String supplierId,
            @RequestBody SupplierRequest request) {
        try {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(supplierId);
            supplier.setName(request.getName());
            supplier.setContact(request.getContact());
            supplier.setLeadTimeDays(request.getLeadTimeDays());
            supplier.setCertifications(request.getCertifications());

            Supplier updated = supplierService.updateSupplier(supplierId, supplier);
            if (updated != null) {
                SupplierResponse response = new SupplierResponse(
                    "Supplier record updated successfully",
                    updated.getSupplierId(),
                    updated.getStatus()
                );
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            SupplierResponse errorResponse = new SupplierResponse(
                "Error updating supplier: " + e.getMessage(),
                null,
                "error"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // POST /api/supplier-performance/event - Supplier Performance API
    @PostMapping("/performance/event")
    public ResponseEntity<?> updateSupplierPerformance(@RequestBody PerformanceRequest request) {
        try {
            Supplier updated = supplierService.updateSupplierPerformance(
                request.getSupplierId(),
                request.getOnTimeDeliveryPercent(),
                request.getDefectRate()
            );
            if (updated != null) {
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error updating performance: " + e.getMessage());
        }
    }

    // GET /api/supplier-performance/{supplierId} - Get supplier performance
    @GetMapping("/performance/{supplierId}")
    public ResponseEntity<Supplier> getSupplierPerformance(@PathVariable String supplierId) {
        Optional<Supplier> supplier = supplierService.getSupplierById(supplierId);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get());
        }
        return ResponseEntity.notFound().build();
    }
}

