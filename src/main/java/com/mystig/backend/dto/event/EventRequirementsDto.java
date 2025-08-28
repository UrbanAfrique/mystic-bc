package com.mystig.backend.dto.event;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventRequirementsDto {
    private Integer ageLimit;
    private String dresscode;
    private List<String> specialRequirements;

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
