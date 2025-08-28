package com.mystig.backend.dto.statistics;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardDto {
    private BigDecimal totalRevenue;
    private Long totalBookings;
    private List<PopularItemDto> topItems;
    private List<SellerPerformanceDto> topSellers;
    private CustomerAnalyticsDto customerAnalytics;
    private Double overallCustomerSatisfaction; // avg rating across reviews

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public List<PopularItemDto> getTopItems() {
        return topItems;
    }

    public void setTopItems(List<PopularItemDto> topItems) {
        this.topItems = topItems;
    }

    public List<SellerPerformanceDto> getTopSellers() {
        return topSellers;
    }

    public void setTopSellers(List<SellerPerformanceDto> topSellers) {
        this.topSellers = topSellers;
    }

    public CustomerAnalyticsDto getCustomerAnalytics() {
        return customerAnalytics;
    }

    public void setCustomerAnalytics(CustomerAnalyticsDto customerAnalytics) {
        this.customerAnalytics = customerAnalytics;
    }

    public Double getOverallCustomerSatisfaction() {
        return overallCustomerSatisfaction;
    }

    public void setOverallCustomerSatisfaction(Double overallCustomerSatisfaction) {
        this.overallCustomerSatisfaction = overallCustomerSatisfaction;
    }
}
