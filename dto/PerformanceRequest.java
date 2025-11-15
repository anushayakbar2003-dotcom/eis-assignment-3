package com.automotive.eis.dto;

public class PerformanceRequest {
    private String supplierId;
    private Double onTimeDeliveryPercent;
    private Double defectRate;
    private Integer returns;

    public PerformanceRequest() {
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public Integer getReturns() {
        return returns;
    }

    public void setReturns(Integer returns) {
        this.returns = returns;
    }
}

