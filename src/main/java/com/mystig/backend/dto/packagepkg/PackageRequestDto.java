package com.mystig.backend.dto.packagepkg;

import com.mystig.backend.model.enums.PackageType;
import com.mystig.backend.model.enums.PackageStatus;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PackageRequestDto {
    private String name;
    private String description;
    private PackageType type;
    private Integer durationDays;
    private Integer durationNights;
    private List<DestinationDto> destinations;
    private InclusionsDto inclusions;
    private List<String> exclusions;
    private PricingDto pricing;
    private List<String> images; // urls
    private List<ItineraryItemDto> itinerary;
    private String availabilityStart; // ISO date string or map to DTO if preferred
    private String availabilityEnd;
    private Integer maxParticipants;
    private Integer minParticipants;
    private UUID ownerId;
    private PackageStatus status;
    private boolean featured;
    private Set<String> tags;

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

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Integer getDurationNights() {
        return durationNights;
    }

    public void setDurationNights(Integer durationNights) {
        this.durationNights = durationNights;
    }

    public List<DestinationDto> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationDto> destinations) {
        this.destinations = destinations;
    }

    public InclusionsDto getInclusions() {
        return inclusions;
    }

    public void setInclusions(InclusionsDto inclusions) {
        this.inclusions = inclusions;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    public PricingDto getPricing() {
        return pricing;
    }

    public void setPricing(PricingDto pricing) {
        this.pricing = pricing;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ItineraryItemDto> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<ItineraryItemDto> itinerary) {
        this.itinerary = itinerary;
    }

    public String getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(String availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public String getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(String availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Integer minParticipants) {
        this.minParticipants = minParticipants;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
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
}
