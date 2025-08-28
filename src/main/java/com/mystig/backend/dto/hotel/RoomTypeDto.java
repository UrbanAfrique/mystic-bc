package com.mystig.backend.dto.hotel;

import com.mystig.backend.model.enums.HotelAmenity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomTypeDto {
    private String name;
    private Integer capacity;
    private BigDecimal price;
    private Set<HotelAmenity> amenities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<HotelAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<HotelAmenity> amenities) {
        this.amenities = amenities;
    }
}
