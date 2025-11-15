package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "returns")
public class Return {
    @Id
    @Column(name = "return_id")
    private String returnId;

    @Column(name = "rma_id", unique = true)
    private String rmaId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1000)
    private String reason;

    private String disposition;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Return() {
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getReturnId() { return returnId; }
    public void setReturnId(String returnId) { this.returnId = returnId; }
    public String getRmaId() { return rmaId; }
    public void setRmaId(String rmaId) { this.rmaId = rmaId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getDisposition() { return disposition; }
    public void setDisposition(String disposition) { this.disposition = disposition; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

