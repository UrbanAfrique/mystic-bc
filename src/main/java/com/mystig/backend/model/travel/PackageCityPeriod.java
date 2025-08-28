package com.mystig.backend.model.travel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Table(name = "package_city_periods")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "specialPackage"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PackageCityPeriod {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private SpecialPackage specialPackage;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private Integer periodDays;
}
