package com.mystig.backend.model.embedded;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportAvailability {
    @ElementCollection
    private List<String> cities = new ArrayList<>();   // e.g., ["Casablanca","Rabat"]

    @ElementCollection
    private List<String> routes = new ArrayList<>();   // e.g., ["CMN->RAK", "RAK->ESSAOUIRA"]

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }
}
