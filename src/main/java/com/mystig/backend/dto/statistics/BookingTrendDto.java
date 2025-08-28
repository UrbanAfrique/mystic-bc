package com.mystig.backend.dto.statistics;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingTrendDto {
    private String period; // date string matching period granularity
    private Long bookings;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getBookings() {
        return bookings;
    }

    public void setBookings(Long bookings) {
        this.bookings = bookings;
    }
}
