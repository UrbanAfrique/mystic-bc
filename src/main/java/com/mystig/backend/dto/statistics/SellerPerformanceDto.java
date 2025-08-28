package com.mystig.backend.dto.statistics;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SellerPerformanceDto {
    private UUID sellerId;
    private String sellerName; // email or name
    private Long bookingsCount;
    private BigDecimal revenue;

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getBookingsCount() {
        return bookingsCount;
    }

    public void setBookingsCount(Long bookingsCount) {
        this.bookingsCount = bookingsCount;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
