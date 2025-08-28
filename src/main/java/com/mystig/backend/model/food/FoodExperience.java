package com.mystig.backend.model.food;

import com.mystig.backend.model.User;
import com.mystig.backend.model.enums.DifficultyLevel;
import com.mystig.backend.model.enums.FoodStatus;
import com.mystig.backend.model.enums.FoodType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "food_experiences", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "type"),
        @Index(columnList = "owner_id"),
        @Index(columnList = "status")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodExperience {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String duration; // e.g., "2h", "Half day"

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency = "MAD";

    private String location;

    @ElementCollection
    @CollectionTable(name = "food_includes", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "include_item")
    private List<String> includes = new ArrayList<>();

    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Enumerated(EnumType.STRING)
    private FoodStatus status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public List<FoodImage> getImages() {
        return images;
    }

    public void setImages(List<FoodImage> images) {
        this.images = images;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public FoodStatus getStatus() {
        return status;
    }

    public void setStatus(FoodStatus status) {
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
