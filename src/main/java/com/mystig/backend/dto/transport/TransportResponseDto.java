package com.mystig.backend.dto.transport;

import com.mystig.backend.model.enums.TransportType;
import com.mystig.backend.model.enums.TransportStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransportResponseDto {
    private UUID id;
    private TransportType type;
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private Integer capacity;
    private Set<String> features;
    private List<String> availabilityCities;
    private List<String> availabilityRoutes;
    private UUID ownerId;
    private TransportStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public List<String> getAvailabilityCities() {
        return availabilityCities;
    }

    public void setAvailabilityCities(List<String> availabilityCities) {
        this.availabilityCities = availabilityCities;
    }

    public List<String> getAvailabilityRoutes() {
        return availabilityRoutes;
    }

    public void setAvailabilityRoutes(List<String> availabilityRoutes) {
        this.availabilityRoutes = availabilityRoutes;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public TransportStatus getStatus() {
        return status;
    }

    public void setStatus(TransportStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
