package com.mystig.backend.model.embedded;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PricingEmbeddable {
    private BigDecimal basePrice;
    private String currency;
    private BigDecimal pricePerPerson;

    @ElementCollection
    @CollectionTable(name = "package_group_discounts")
    private List<String> groupDiscounts = new ArrayList<>(); // store as JSON-like strings or create a small embeddable if needed

    @ElementCollection
    @CollectionTable(name = "package_seasonal_prices")
    private List<PriceSeasonEmbeddable> seasonal = new ArrayList<>();

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

    public BigDecimal getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(BigDecimal pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public List<String> getGroupDiscounts() {
        return groupDiscounts;
    }

    public void setGroupDiscounts(List<String> groupDiscounts) {
        this.groupDiscounts = groupDiscounts;
    }

    public List<PriceSeasonEmbeddable> getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(List<PriceSeasonEmbeddable> seasonal) {
        this.seasonal = seasonal;
    }
}
