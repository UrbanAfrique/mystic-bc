package com.mystig.backend.dto.statistics;

import lombok.*;

import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerAnalyticsDto {
    // map country -> count
    private Map<String, Long> customersByCountry;
    // additional fields can be added when more user data exists (age groups, gender)


    public Map<String, Long> getCustomersByCountry() {
        return customersByCountry;
    }

    public void setCustomersByCountry(Map<String, Long> customersByCountry) {
        this.customersByCountry = customersByCountry;
    }
}
