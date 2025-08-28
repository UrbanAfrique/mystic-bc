package com.mystig.backend.model.food;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "food_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FoodImage {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String url;
    private String caption;
    private boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY)
    private FoodExperience food;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public FoodExperience getFood() {
        return food;
    }

    public void setFood(FoodExperience food) {
        this.food = food;
    }
}
