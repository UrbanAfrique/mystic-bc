package com.mystig.backend.dto.hotel;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PricingDto {
    private BigDecimal basePrice;
    private String currency;
    private List<SeasonalPriceDto> seasonal;

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

    public List<SeasonalPriceDto> getSeasonal() {
        return seasonal;
    }

    public void setSeasonal(List<SeasonalPriceDto> seasonal) {
        this.seasonal = seasonal;
    }
}
