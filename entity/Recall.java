package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recalls")
public class Recall {
    @Id
    @Column(name = "recall_id")
    private String recallId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(length = 1000)
    private String reason;

    @Column(length = 1000)
    private String scope;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Recall() {
        this.status = "ISSUED";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getRecallId() { return recallId; }
    public void setRecallId(String recallId) { this.recallId = recallId; }
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

