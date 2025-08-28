package com.mystig.backend.model.transport;

import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.TransportAvailability;
import com.mystig.backend.model.enums.TransportStatus;
import com.mystig.backend.model.enums.TransportType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "transports", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "type"),
        @Index(columnList = "owner_id"),
        @Index(columnList = "status")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transport {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportType type;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency = "MAD";

    private Integer capacity;

    @ElementCollection
    @CollectionTable(name = "transport_features", joinColumns = @JoinColumn(name = "transport_id"))
    @Column(name = "feature")
    private Set<String> features = new HashSet<>();

    @Embedded
    private TransportAvailability availability;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Enumerated(EnumType.STRING)
    private TransportStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
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

    public TransportAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(TransportAvailability availability) {
        this.availability = availability;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
