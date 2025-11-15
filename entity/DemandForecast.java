package com.automotive.eis.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id")
    private Long forecastId;

    @Column(name = "part_id", nullable = false)
    private String partId;

    @Column(name = "forecast_units", nullable = false)
    private Integer forecastUnits;

    @Column(name = "confidence_level")
    private String confidenceLevel;

    @Column(name = "forecast_date", nullable = false)
    private LocalDate forecastDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public DemandForecast() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getForecastId() { return forecastId; }
    public void setForecastId(Long forecastId) { this.forecastId = forecastId; }
    public String getPartId() { return partId; }
    public void setPartId(String partId) { this.partId = partId; }
    public Integer getForecastUnits() { return forecastUnits; }
    public void setForecastUnits(Integer forecastUnits) { this.forecastUnits = forecastUnits; }
    public String getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(String confidenceLevel) { this.confidenceLevel = confidenceLevel; }
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

