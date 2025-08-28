package com.mystig.backend.model.embedded;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessInfo {
    private String companyName;
    private String license;
    @ElementCollection
    private Set<String> specialties = new HashSet<>();
    private String description;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Set<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<String> specialties) {
        this.specialties = specialties;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
