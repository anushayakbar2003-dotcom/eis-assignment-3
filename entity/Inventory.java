package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(name = "on_hand")
    private Integer onHand = 0;

    @Column(name = "reserved")
    private Integer reserved = 0;

    @Column(name = "available")
    private Integer available = 0;

    @Column(name = "reorder_point")
    private Integer reorderPoint = 0;

    @Column(name = "safety_stock")
    private Integer safetyStock = 0;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "bin_location")
    private String binLocation;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructors
    public Inventory() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Inventory(String partId, Integer onHand) {
        this.partId = partId;
        this.onHand = onHand;
        this.reserved = 0;
        this.available = onHand;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
        this.available = onHand - reserved;
        this.lastUpdated = LocalDateTime.now();
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
        this.available = onHand - reserved;
        this.lastUpdated = LocalDateTime.now();
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

