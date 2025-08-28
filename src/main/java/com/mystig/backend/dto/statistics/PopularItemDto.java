package com.mystig.backend.dto.statistics;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PopularItemDto {
    private UUID itemId;
    private String itemType; // HOTEL | PACKAGE | EVENT
    private String itemName; // resolved name when available
    private Long bookings;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getBookings() {
        return bookings;
    }

    public void setBookings(Long bookings) {
        this.bookings = bookings;
    }
}
