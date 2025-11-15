package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @Column(name = "supplier_id")
    private String supplierId;

    @Column(nullable = false)
    private String name;

    private String contact;
    
    @Column(name = "lead_time_days")
    private Integer leadTimeDays;
    
    @Column(length = 1000)
    private String certifications;
    
    @Column(name = "on_time_delivery_percent")
    private Double onTimeDeliveryPercent;
    
    @Column(name = "defect_rate")
    private Double defectRate;
    
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public Supplier() {
    }

    public Supplier(String supplierId, String name, String contact, Integer leadTimeDays) {
        this.supplierId = supplierId;
        this.name = name;
        this.contact = contact;
        this.leadTimeDays = leadTimeDays;
        this.status = "active";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public Double getOnTimeDeliveryPercent() {
        return onTimeDeliveryPercent;
    }

    public void setOnTimeDeliveryPercent(Double onTimeDeliveryPercent) {
        this.onTimeDeliveryPercent = onTimeDeliveryPercent;
    }

    public Double getDefectRate() {
        return defectRate;
    }

    public void setDefectRate(Double defectRate) {
        this.defectRate = defectRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

