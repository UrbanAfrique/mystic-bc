package com.mystig.backend.model.embedded;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pricing {
    private BigDecimal basePrice;
    private String currency;

    @ElementCollection
    @CollectionTable(name = "hotel_seasonal_prices")
    private List<SeasonalPrice> seasonal = new ArrayList<>();

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<SeasonalPrice> getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(List<SeasonalPrice> seasonal) {
        this.seasonal = seasonal;
    }
}
