package com.mystig.backend.mapper;

import com.mystig.backend.dto.event.*;
import com.mystig.backend.model.embedded.*;
import com.mystig.backend.model.event.*;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EventMapper {

    public Event toEntity(EventRequestDto r, com.mystig.backend.model.User organizer) {
    if (r == null) {
        throw new IllegalArgumentException("EventRequestDto cannot be null");
    }

    Event e = Event.builder()
            .title(r.getTitle())
            .description(r.getDescription())
            .type(r.getType())
            .category(r.getCategory())
            .dateRange(EventDateRange.builder()
                    .start(r.getStartDate())
                    .end(r.getEndDate())
                    .build())
            .timeRange(EventTimeRange.builder()
                    .start(r.getStartTime())
                    .end(r.getEndTime())
                    .build())
            .location(EventLocation.builder()
                    .venue(r.getVenue())
                    .address(r.getAddress())
                    .city(r.getCity())
                    .coordinates(Coordinates.builder()
                            .lat(r.getLat())
                            .lng(r.getLng())
                            .build())
                    .build())
            .organizer(organizer)
            .status(r.getStatus())
            .featured(r.isFeatured())
            .build();

    // Tags
    if (r.getTags() != null && !r.getTags().isEmpty()) {
        e.getTags().addAll(r.getTags());
    }

    // Social media
    if (r.getSocialMedia() != null) {
        e.setSocialMedia(SocialMedia.builder()
                .facebook(r.getSocialMedia().getFacebook())
                .instagram(r.getSocialMedia().getInstagram())
                .twitter(r.getSocialMedia().getTwitter())
                .website(r.getSocialMedia().getWebsite())
                .build());
    }

    // Requirements
    if (r.getRequirements() != null) {
        e.setRequirements(EventRequirements.builder()
                .ageLimit(r.getRequirements().getAgeLimit())
                .dresscode(r.getRequirements().getDresscode())
                .specialRequirements(r.getRequirements().getSpecialRequirements())
                .build());
    }

    // Images
    if (r.getImages() != null && !r.getImages().isEmpty()) {
        r.getImages().stream()
                .map(i -> EventImage.builder()
                        .url(i.getUrl())
                        .caption(i.getCaption())
                        .isPrimary(i.isPrimary())
                        .event(e)
                        .build())
                .forEach(e.getImages()::add);
    }

    // Tickets
    if (r.getTickets() != null && !r.getTickets().isEmpty()) {
        r.getTickets().stream()
            .map(t -> Ticket.builder()
                .typeName(t.getTypeName())
                .price(t.getPrice())
                .currency(t.getCurrency())
                .quantity(t.getQuantity())
                .sold(0)
                .event(e)
                .build())
            .forEach(e.getTickets()::add);
    }

    return e;
}


    public EventResponseDto toDto(Event e) {
        return EventResponseDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .type(e.getType())
                .category(e.getCategory())
                .startDate(e.getDateRange() == null ? null : e.getDateRange().getStart())
                .endDate(e.getDateRange() == null ? null : e.getDateRange().getEnd())
                .startTime(e.getTimeRange() == null ? null : e.getTimeRange().getStart())
                .endTime(e.getTimeRange() == null ? null : e.getTimeRange().getEnd())
                .venue(e.getLocation() == null ? null : e.getLocation().getVenue())
                .address(e.getLocation() == null ? null : e.getLocation().getAddress())
                .city(e.getLocation() == null ? null : e.getLocation().getCity())
                .lat(e.getLocation() == null || e.getLocation().getCoordinates() == null ? null : e.getLocation().getCoordinates().getLat())
                .lng(e.getLocation() == null || e.getLocation().getCoordinates() == null ? null : e.getLocation().getCoordinates().getLng())
                .images(e.getImages() == null ? List.of() : e.getImages().stream().map(i -> ImageDto.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).build()).toList())
                .tickets(e.getTickets() == null ? List.of() : e.getTickets().stream().map(t -> TicketPricingDto.builder()
                        .typeName(t.getTypeName())
                        .price(t.getPrice())
                        .currency(t.getCurrency())
                        .quantity(t.getQuantity())
                        .sold(t.getSold()) // <-- This now works
                        .build()).toList())
                .organizerId(e.getOrganizer() == null ? null : e.getOrganizer().getId())
                .status(e.getStatus())
                .featured(e.isFeatured())
                .tags(e.getTags())
                .socialMedia(e.getSocialMedia() == null ? null : SocialMediaDto.builder()
                        .facebook(e.getSocialMedia().getFacebook())
                        .instagram(e.getSocialMedia().getInstagram())
                        .twitter(e.getSocialMedia().getTwitter())
                        .website(e.getSocialMedia().getWebsite())
                        .build())
                .requirements(e.getRequirements() == null ? null : EventRequirementsDto.builder()
                        .ageLimit(e.getRequirements().getAgeLimit())
                        .dresscode(e.getRequirements().getDresscode())
                        .specialRequirements(e.getRequirements().getSpecialRequirements())
                        .build())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public AttendeeResponseDto attendeeToDto(com.mystig.backend.model.event.Attendee a) {
        return AttendeeResponseDto.builder()
                .id(a.getId())
                .userId(a.getUser() == null ? null : a.getUser().getId())
                .name(a.getName())
                .email(a.getEmail())
                .phone(a.getPhone())
                .ticketId(a.getTicket() == null ? null : a.getTicket().getId())
                .createdAt(a.getCreatedAt())
                .build();
    }
}

