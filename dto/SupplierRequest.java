package com.automotive.eis.dto;

public class SupplierRequest {
    private String supplierId;
    private String name;
    private String contact;
    private Integer leadTimeDays;
    private String certifications;

    // Constructors
    public SupplierRequest() {
    }

    public SupplierRequest(String supplierId, String name, String contact, Integer leadTimeDays) {
        this.supplierId = supplierId;
        this.name = name;
        this.contact = contact;
        this.leadTimeDays = leadTimeDays;
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
}

