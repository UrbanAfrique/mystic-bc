package com.mystig.backend.dto.event;

import com.mystig.backend.model.enums.*;
import com.mystig.backend.dto.event.TicketPricingDto;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventRequestDto {
    private String title;
    private String description;
    private EventType type;
    private EventCategory category;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private String address;
    private String city;
    private Double lat;
    private Double lng;
    private List<ImageDto> images;
    private List<TicketPricingDto> tickets;
    private UUID organizerId;
    private EventStatus status;
    private boolean featured;
    private Set<String> tags;
    private SocialMediaDto socialMedia;
    private EventRequirementsDto requirements;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public List<TicketPricingDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketPricingDto> tickets) {
        this.tickets = tickets;
    }

    public UUID getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(UUID organizerId) {
        this.organizerId = organizerId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
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

    public SocialMediaDto getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMediaDto socialMedia) {
        this.socialMedia = socialMedia;
    }

    public EventRequirementsDto getRequirements() {
        return requirements;
    }

    public void setRequirements(EventRequirementsDto requirements) {
        this.requirements = requirements;
    }
}
