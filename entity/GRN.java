package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grns")
public class GRN {
    @Id
    @Column(name = "grn_id")
    private String grnId;

    @Column(name = "po_id", nullable = false)
    private String poId;

    @Column(name = "asn_id")
    private String asnId;

    @Column(name = "received_qty", nullable = false)
    private Integer receivedQty;

    @Column(name = "inspected_qty")
    private Integer inspectedQty;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    private String inspector;

    private String status;

    // Constructors
    public GRN() {
        this.status = "POSTED";
        this.receivedAt = LocalDateTime.now();
    }

    public GRN(String grnId, String poId, Integer receivedQty) {
        this.grnId = grnId;
        this.poId = poId;
        this.receivedQty = receivedQty;
        this.status = "POSTED";
        this.receivedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Integer getInspectedQty() {
        return inspectedQty;
    }

    public void setInspectedQty(Integer inspectedQty) {
        this.inspectedQty = inspectedQty;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

