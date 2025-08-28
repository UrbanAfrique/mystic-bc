package com.mystig.backend.model.hotel;

import com.mystig.backend.model.enums.HotelAmenity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hotel_room_types")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomType {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private Integer capacity;
    private BigDecimal price;

    @ElementCollection(targetClass = HotelAmenity.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "hotel_room_type_amenities", joinColumns = @JoinColumn(name = "room_type_id"))
    @Column(name = "amenity")
    private Set<HotelAmenity> amenities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
