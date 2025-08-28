package com.mystig.backend.model.embedded;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItineraryItemEmbeddable {
    private Integer day;
    private String title;
    private String description;

    private String activitiesJson = "[]"; // store as JSON string
    private String mealsJson = "[]"; // store as JSON string

    private String accommodation;

    // Utility methods to get/set lists
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

    public List<String> getMealsList() {
        try {
            return new ObjectMapper().readValue(this.mealsJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void setMealsList(List<String> meals) {
        try {
            this.mealsJson = new ObjectMapper().writeValueAsString(meals == null ? List.of() : meals);
        } catch (Exception e) {
            this.mealsJson = "[]";
        }
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivitiesJson() {
        return activitiesJson;
    }

    public void setActivitiesJson(String activitiesJson) {
        this.activitiesJson = activitiesJson;
    }

    public String getMealsJson() {
        return mealsJson;
    }

    public void setMealsJson(String mealsJson) {
        this.mealsJson = mealsJson;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }
}
