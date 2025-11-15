package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asns")
public class ASN {
    @Id
    @Column(name = "asn_id")
    private String asnId;

    @Column(name = "po_id", nullable = false)
    private String poId;

    @Column(name = "supplier_id", nullable = false)
    private String supplierId;

    @Column(name = "eta")
    private LocalDateTime eta;

    private String carrier;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ASN() {
        this.status = "RECEIVED";
        this.createdAt = LocalDateTime.now();
    }

    public ASN(String asnId, String poId, String supplierId) {
        this.asnId = asnId;
        this.poId = poId;
        this.supplierId = supplierId;
        this.status = "RECEIVED";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAsnId() { return asnId; }
    public void setAsnId(String asnId) { this.asnId = asnId; }
    public String getPoId() { return poId; }
    public void setPoId(String poId) { this.poId = poId; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public LocalDateTime getEta() { return eta; }
    public void setEta(LocalDateTime eta) { this.eta = eta; }
    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

