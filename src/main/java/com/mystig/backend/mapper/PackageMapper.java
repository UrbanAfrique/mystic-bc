package com.mystig.backend.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystig.backend.dto.packagepkg.*;
import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.*;
import com.mystig.backend.model.packagepkg.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class PackageMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TravelPackage toEntity(PackageRequestDto r, User owner) {
        TravelPackage p = new TravelPackage();
        p.setName(r.getName());
        p.setDescription(r.getDescription());
        p.setType(r.getType());
        p.setDuration(DurationEmbeddable.builder()
                .days(r.getDurationDays())
                .nights(r.getDurationNights())
                .build()
        );

        if (r.getDestinations() != null) {
            List<DestinationEmbeddable> dest = r.getDestinations().stream().map(d ->
                    DestinationEmbeddable.builder()
                            .city(d.getCity())
                            .region(d.getRegion())
                            .durationDays(d.getDurationDays())
                            .activitiesJson(writeJson(d.getActivities())) // ✅ now stores JSON
                            .build()
            ).toList();
            p.getDestinations().addAll(dest);
        }
        if (r.getInclusions() != null) {
            p.setInclusions(InclusionsEmbeddable.builder()
                    .accommodation(r.getInclusions().getAccommodation())
                    .meals(r.getInclusions().getMeals())
                    .transport(r.getInclusions().getTransport())
                    .guide(r.getInclusions().getGuide())
                    .activities(r.getInclusions().getActivities() == null ? List.of() : r.getInclusions().getActivities())
                    .other(r.getInclusions().getOther() == null ? List.of() : r.getInclusions().getOther())
                    .build());
        }

        if (r.getExclusions() != null) {
            p.setExclusions(r.getExclusions());
        }

        if (r.getPricing() != null) {
            PricingEmbeddable pr = PricingEmbeddable.builder()
                    .basePrice(r.getPricing().getBasePrice())
                    .currency(r.getPricing().getCurrency())
                    .pricePerPerson(r.getPricing().getPricePerPerson())
                    .groupDiscounts(r.getPricing().getGroupDiscounts() == null ? List.of() : r.getPricing().getGroupDiscounts())
                    .seasonal(r.getPricing().getSeasonal() == null ? List.of() :
                            r.getPricing().getSeasonal().stream().map(s -> PriceSeasonEmbeddable.builder()
                                    .season(s.getSeason())
                                    .multiplier(s.getMultiplier())
                                    .startDate(s.getStartDate())
                                    .endDate(s.getEndDate())
                                    .build()).toList())
                    .build();
            p.setPricing(pr);
        }

        if (r.getImages() != null) {
            List<PackageImage> imgs = r.getImages().stream().map(url ->
                    PackageImage.builder()
                            .url(url)
                            .caption(null)
                            .isPrimary(false)
                            .travelPackage(p)
                            .build()
            ).toList();
            p.getImages().addAll(imgs);
        }

        if (r.getItinerary() != null) {
            List<ItineraryItemEmbeddable> it = r.getItinerary().stream().map(i -> {
                ItineraryItemEmbeddable item = ItineraryItemEmbeddable.builder()
                        .day(i.getDay())
                        .title(i.getTitle())
                        .description(i.getDescription())
                        .accommodation(i.getAccommodation())
                        .activitiesJson(writeJson(i.getActivities()))
                        .mealsJson(writeJson(i.getMeals()))
                        .build();
                return item;
            }).toList();
            p.getItinerary().addAll(it);
        }

        if (r.getAvailabilityStart() != null || r.getAvailabilityEnd() != null) {
            p.setAvailability(AvailabilityEmbeddable.builder()
                    .startDate(r.getAvailabilityStart() == null ? null : LocalDate.parse(r.getAvailabilityStart()))
                    .endDate(r.getAvailabilityEnd() == null ? null : LocalDate.parse(r.getAvailabilityEnd()))
                    .maxParticipants(r.getMaxParticipants())
                    .minParticipants(r.getMinParticipants())
                    .build());
        }

        p.setOwner(owner);
        p.setStatus(r.getStatus());
        p.setFeatured(r.isFeatured());
        if (r.getTags() != null) {
            p.setTags(r.getTags());
        }

        // initialize rating
        p.setRating(RatingEmbeddable.builder().average(0.0).count(0L).build());

        return p;
    }

    private static String writeJson(List<String> list) {
        try {
            return new ObjectMapper().writeValueAsString(list == null ? List.of() : list);
        } catch (Exception e) {
            return "[]";
        }
    }

    public PackageResponseDto toDto(TravelPackage p) {
        return PackageResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .type(p.getType())
                .durationDays(p.getDuration() == null ? null : p.getDuration().getDays())
                .durationNights(p.getDuration() == null ? null : p.getDuration().getNights())
                .destinations(p.getDestinations() == null ? List.of() : p.getDestinations().stream().map(d -> DestinationDto.builder()
                        .city(d.getCity())
                        .region(d.getRegion())
                        .durationDays(d.getDurationDays())
                        .activities(d.getActivitiesList()) // ✅ fixed: use getter that parses JSON
                        .build()).toList())
                .inclusions(p.getInclusions() == null ? null : InclusionsDto.builder()
                        .accommodation(p.getInclusions().getAccommodation())
                        .meals(p.getInclusions().getMeals())
                        .transport(p.getInclusions().getTransport())
                        .guide(p.getInclusions().getGuide())
                        .activities(p.getInclusions().getActivities())
                        .other(p.getInclusions().getOther())
                        .build())
                .exclusions(p.getExclusions())
                .pricing(p.getPricing() == null ? null : PricingDto.builder()
                        .basePrice(p.getPricing().getBasePrice())
                        .currency(p.getPricing().getCurrency())
                        .pricePerPerson(p.getPricing().getPricePerPerson())
                        .groupDiscounts(p.getPricing().getGroupDiscounts())
                        .seasonal(p.getPricing().getSeasonal() == null ? List.of() : p.getPricing().getSeasonal().stream().map(s -> PriceSeasonDto.builder()
                                .season(s.getSeason())
                                .multiplier(s.getMultiplier())
                                .startDate(s.getStartDate())
                                .endDate(s.getEndDate())
                                .build()).toList())
                        .build())
                .images(p.getImages() == null ? List.of() : p.getImages().stream().map(PackageImage::getUrl).toList())
                .itinerary(p.getItinerary() == null ? List.of() : p.getItinerary().stream().map(i -> ItineraryItemDto.builder()
                        .day(i.getDay())
                        .title(i.getTitle())
                        .description(i.getDescription())
                        .activities(i.getActivitiesList()) // ✅ fixed
                        .meals(i.getMealsList())           // ✅ fixed
                        .accommodation(i.getAccommodation())
                        .build()).toList())
                .availabilityStart(p.getAvailability() == null || p.getAvailability().getStartDate() == null ? null : p.getAvailability().getStartDate().toString())
                .availabilityEnd(p.getAvailability() == null || p.getAvailability().getEndDate() == null ? null : p.getAvailability().getEndDate().toString())
                .maxParticipants(p.getAvailability() == null ? null : p.getAvailability().getMaxParticipants())
                .minParticipants(p.getAvailability() == null ? null : p.getAvailability().getMinParticipants())
                .ownerId(p.getOwner() == null ? null : p.getOwner().getId())
                .status(p.getStatus())
                .bookingsTotal(p.getBookingsTotal())
                .bookingsThisMonth(p.getBookingsThisMonth())
                .featured(p.isFeatured())
                .tags(p.getTags())
                .ratingAverage(p.getRating() == null ? 0.0 : p.getRating().getAverage())
                .ratingCount(p.getRating() == null ? 0L : p.getRating().getCount())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
