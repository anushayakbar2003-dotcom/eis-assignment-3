package com.automotive.eis.dto;

public class SupplierResponse {
    private String message;
    private String supplierId;
    private String status;

    public SupplierResponse() {
    }

    public SupplierResponse(String message, String supplierId, String status) {
        this.message = message;
        this.supplierId = supplierId;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

