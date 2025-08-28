package com.mystig.backend.model.embedded;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InclusionsEmbeddable {

    private String accommodation;
    private String meals;
    private String transport;
    private String guide;

    @ElementCollection
    @CollectionTable(
            name = "inclusions_activities",
            joinColumns = @JoinColumn(name = "package_id")
    )
    @Column(name = "activity")
    private List<String> activities = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "inclusions_other",
            joinColumns = @JoinColumn(name = "package_id")
    )
    @Column(name = "other_item")
    private List<String> other = new ArrayList<>();

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public List<String> getOther() {
        return other;
    }

    public void setOther(List<String> other) {
        this.other = other;
    }
}
