package com.mystig.backend.model.packagepkg;
import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.*;
import com.mystig.backend.model.enums.PackageStatus;
import com.mystig.backend.model.enums.PackageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.*;
@Entity
@Table(name = "packages", indexes = { @Index(columnList = "name"), @Index(columnList = "type"), @Index(columnList = "status"), @Index(columnList = "owner_id") })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TravelPackage {
    @Id @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private PackageType type;
    @Embedded private DurationEmbeddable duration;
    @ElementCollection @CollectionTable(name = "package_destinations", joinColumns = @JoinColumn(name = "package_id")) private List<DestinationEmbeddable> destinations = new ArrayList<>(); @Embedded private InclusionsEmbeddable inclusions; @ElementCollection private List<String> exclusions = new ArrayList<>(); @Embedded private PricingEmbeddable pricing; @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true) private List<PackageImage> images = new ArrayList<>(); @ElementCollection @CollectionTable(name = "package_itinerary", joinColumns = @JoinColumn(name = "package_id")) private List<ItineraryItemEmbeddable> itinerary = new ArrayList<>(); @Embedded private AvailabilityEmbeddable availability; @Embedded private RatingEmbeddable rating; @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true) private List<PackageReview> reviews = new ArrayList<>(); @ManyToOne(fetch = FetchType.LAZY) private User owner; @Enumerated(EnumType.STRING) private PackageStatus status; private Long bookingsTotal; private Long bookingsThisMonth; private boolean featured; @ElementCollection private Set<String> tags = new HashSet<>(); @CreationTimestamp private Instant createdAt; @UpdateTimestamp private Instant updatedAt;

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

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public DurationEmbeddable getDuration() {
        return duration;
    }

    public void setDuration(DurationEmbeddable duration) {
        this.duration = duration;
    }

    public List<DestinationEmbeddable> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationEmbeddable> destinations) {
        this.destinations = destinations;
    }

    public InclusionsEmbeddable getInclusions() {
        return inclusions;
    }

    public void setInclusions(InclusionsEmbeddable inclusions) {
        this.inclusions = inclusions;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    public PricingEmbeddable getPricing() {
        return pricing;
    }

    public void setPricing(PricingEmbeddable pricing) {
        this.pricing = pricing;
    }

    public List<PackageImage> getImages() {
        return images;
    }

    public void setImages(List<PackageImage> images) {
        this.images = images;
    }

    public List<ItineraryItemEmbeddable> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<ItineraryItemEmbeddable> itinerary) {
        this.itinerary = itinerary;
    }

    public AvailabilityEmbeddable getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityEmbeddable availability) {
        this.availability = availability;
    }

    public RatingEmbeddable getRating() {
        return rating;
    }

    public void setRating(RatingEmbeddable rating) {
        this.rating = rating;
    }

    public List<PackageReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<PackageReview> reviews) {
        this.reviews = reviews;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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