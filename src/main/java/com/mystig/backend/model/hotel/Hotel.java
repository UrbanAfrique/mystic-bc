package com.mystig.backend.model.hotel;

import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.*;
import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;
import java.util.stream.DoubleStream;

@Entity
@Table(name = "hotels", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "status"),
        @Index(columnList = "featured"),
        @Index(columnList = "owner_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Hotel {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Embedded
    private HotelLocation location;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelImage> images = new ArrayList<>();

    @ElementCollection(targetClass = HotelAmenity.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private Set<HotelAmenity> amenities = new HashSet<>();

    // simple counters
    private Integer roomsTotal;
    private Integer roomsAvailable;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomType> roomTypes = new ArrayList<>();

    @Embedded
    private Pricing pricing;

    @Embedded
    @Builder.Default
    private Rating rating = Rating.builder().average(0.0).count(0L).build();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelReview> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    private HotelStatus status;

    // derived counters (updated by service as bookings happen)
    private Long bookingsTotal;
    private Long bookingsThisMonth;

    private boolean featured;

    @CreationTimestamp
    private java.time.Instant createdAt;

    @UpdateTimestamp
    private java.time.Instant updatedAt;

    // helper
    public void recalcRating() {
        if (reviews == null || reviews.isEmpty()) {
            rating.setAverage(0.0);
            rating.setCount(0L);
            return;
        }
        double avg = reviews.stream().flatMapToDouble(r -> DoubleStream.of(r.getRating())).average().orElse(0.0);
        rating.setAverage(Math.round(avg * 10.0) / 10.0);
        rating.setCount((long) reviews.size());
    }

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

    public HotelLocation getLocation() {
        return location;
    }

    public void setLocation(HotelLocation location) {
        this.location = location;
    }

    public List<HotelImage> getImages() {
        return images;
    }

    public void setImages(List<HotelImage> images) {
        this.images = images;
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

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<HotelReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotelReview> reviews) {
        this.reviews = reviews;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
