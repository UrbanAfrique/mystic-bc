package com.mystig.backend.dto.hotel;

import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelRequest {
    private String name;
    private String description;
    private LocationDto location;
    private Set<HotelAmenity> amenities;
    private Integer roomsTotal;
    private Integer roomsAvailable;
    private PricingDto pricing;
    private List<ImageDto> images;
    private List<RoomTypeDto> roomTypes;
    private HotelStatus status;
    private boolean featured;
    private UUID ownerId;

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

    public HotelStatus getStatus() {
        return status;
    }

    public void setStatus(HotelStatus status) {
        this.status = status;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
