package com.mystig.backend.model.embedded;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventRequirements {
    private Integer ageLimit;
    private String dresscode;

    @ElementCollection
    private List<String> specialRequirements = new ArrayList<>();

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getDresscode() {
        return dresscode;
    }

    public void setDresscode(String dresscode) {
        this.dresscode = dresscode;
    }

    public List<String> getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(List<String> specialRequirements) {
        this.specialRequirements = specialRequirements;
    }
}
