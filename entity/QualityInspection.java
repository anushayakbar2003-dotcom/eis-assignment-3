package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quality_inspections")
public class QualityInspection {
    @Id
    @Column(name = "inspection_id")
    private String inspectionId;

    @Column(name = "grn_id")
    private String grnId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(name = "lot_number")
    private String lotNumber;

    private String inspector;

    @Column(nullable = false)
    private String result;

    @Column(name = "defect_code")
    private String defectCode;

    @Column(name = "sample_size")
    private Integer sampleSize;

    @Column(name = "failed_qty")
    private Integer failedQty;

    @Column(length = 1000)
    private String notes;

    @Column(name = "inspection_date")
    private LocalDateTime inspectionDate;

    public QualityInspection() {
        this.inspectionDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getInspectionId() { return inspectionId; }
    public void setInspectionId(String inspectionId) { this.inspectionId = inspectionId; }
    public String getGrnId() { return grnId; }
    public void setGrnId(String grnId) { this.grnId = grnId; }
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public String getInspector() { return inspector; }
    public void setInspector(String inspector) { this.inspector = inspector; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getDefectCode() { return defectCode; }
    public void setDefectCode(String defectCode) { this.defectCode = defectCode; }
    public Integer getSampleSize() { return sampleSize; }
    public void setSampleSize(Integer sampleSize) { this.sampleSize = sampleSize; }
    public Integer getFailedQty() { return failedQty; }
    public void setFailedQty(Integer failedQty) { this.failedQty = failedQty; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getInspectionDate() { return inspectionDate; }
    public void setInspectionDate(LocalDateTime inspectionDate) { this.inspectionDate = inspectionDate; }
}

