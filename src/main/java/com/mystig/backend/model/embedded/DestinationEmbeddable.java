package com.mystig.backend.model.embedded;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DestinationEmbeddable {
    private String city;
    private String region;
    private Integer durationDays;

    // Store as JSON string instead of @ElementCollection
    private String activitiesJson = "[]";

    public List<String> getActivitiesList() {
        try {
            return new ObjectMapper().readValue(this.activitiesJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void setActivitiesList(List<String> activities) {
        try {
            this.activitiesJson = new ObjectMapper().writeValueAsString(activities == null ? List.of() : activities);
        } catch (Exception e) {
            this.activitiesJson = "[]";
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public String getActivitiesJson() {
        return activitiesJson;
    }

    public void setActivitiesJson(String activitiesJson) {
        this.activitiesJson = activitiesJson;
    }
}
