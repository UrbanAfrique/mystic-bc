package com.mystig.backend.dto.hotel;

import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelResponse {
    private UUID id;
    private String name;
    private String description;
    private LocationDto location;
    private Set<HotelAmenity> amenities;
    private Integer roomsTotal;
    private Integer roomsAvailable;
    private PricingDto pricing;
    private Double ratingAverage;
    private Long ratingCount;
    private List<ImageDto> images;
    private List<RoomTypeDto> roomTypes;
    private UUID ownerId;
    private HotelStatus status;
    private Long bookingsTotal;
    private Long bookingsThisMonth;
    private boolean featured;
    private Instant createdAt;
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public Set<HotelAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<HotelAmenity> amenities) {
        this.amenities = amenities;
    }

    public Integer getRoomsTotal() {
        return roomsTotal;
    }

    public void setRoomsTotal(Integer roomsTotal) {
        this.roomsTotal = roomsTotal;
    }

    public Integer getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(Integer roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    public PricingDto getPricing() {
        return pricing;
    }

    public void setPricing(PricingDto pricing) {
        this.pricing = pricing;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public List<RoomTypeDto> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomTypeDto> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public HotelStatus getStatus() {
        return status;
    }

    public void setStatus(HotelStatus status) {
        this.status = status;
    }

    public Long getBookingsTotal() {
        return bookingsTotal;
    }

    public void setBookingsTotal(Long bookingsTotal) {
        this.bookingsTotal = bookingsTotal;
    }

    public Long getBookingsThisMonth() {
        return bookingsThisMonth;
    }

    public void setBookingsThisMonth(Long bookingsThisMonth) {
        this.bookingsThisMonth = bookingsThisMonth;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
