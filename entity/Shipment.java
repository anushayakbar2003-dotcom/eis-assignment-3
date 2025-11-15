package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @Column(name = "shipment_id")
    private String shipmentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(nullable = false)
    private Integer quantity;

    private String destination;
    private String carrier;

    @Column(name = "ship_date")
    private LocalDate shipDate;

    @Column(name = "eta")
    private LocalDateTime eta;

    private String status;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "delivered_by")
    private String deliveredBy;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    public Shipment() {
        this.status = "SCHEDULED";
    }

    // Getters and Setters
    public String getShipmentId() { return shipmentId; }
    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }
    public LocalDate getShipDate() { return shipDate; }
    public void setShipDate(LocalDate shipDate) { this.shipDate = shipDate; }
    public LocalDateTime getEta() { return eta; }
    public void setEta(LocalDateTime eta) { this.eta = eta; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getDeliveredBy() { return deliveredBy; }
    public void setDeliveredBy(String deliveredBy) { this.deliveredBy = deliveredBy; }
    public LocalDateTime getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(LocalDateTime deliveryTime) { this.deliveryTime = deliveryTime; }
}

