package com.mystig.backend.dto.packagepkg;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PricingDto {
    private BigDecimal basePrice;
    private String currency;
    private BigDecimal pricePerPerson;
    private List<String> groupDiscounts;
    private List<PriceSeasonDto> seasonal;

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

    public List<PriceSeasonDto> getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(List<PriceSeasonDto> seasonal) {
        this.seasonal = seasonal;
    }
}
