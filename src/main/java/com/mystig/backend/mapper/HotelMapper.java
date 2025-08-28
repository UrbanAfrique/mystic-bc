package com.mystig.backend.mapper;

import com.mystig.backend.dto.hotel.*;
import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.*;
import com.mystig.backend.model.hotel.*;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class HotelMapper {

    public Hotel toEntity(HotelRequest r, User owner) {
        Hotel h = new Hotel();
        h.setName(r.getName());
        h.setDescription(r.getDescription());
        if (r.getLocation() != null) {
            h.setLocation(HotelLocation.builder()
                    .address(r.getLocation().getAddress())
                    .city(r.getLocation().getCity())
                    .region(r.getLocation().getRegion())
                    .coordinates(Coordinates.builder()
                            .lat(r.getLocation().getLat())
                            .lng(r.getLocation().getLng()).build())
                    .build());
        }
        h.setAmenities(r.getAmenities());
        h.setRoomsTotal(r.getRoomsTotal());
        h.setRoomsAvailable(r.getRoomsAvailable());
        if (r.getPricing() != null) {
            PricingDto p = r.getPricing();
            Pricing pricing = Pricing.builder()
                    .basePrice(p.getBasePrice())
                    .currency(p.getCurrency())
                    .seasonal(p.getSeasonal() == null ? List.of() :
                            p.getSeasonal().stream().map(s ->
                                    SeasonalPrice.builder()
                                            .season(s.getSeason())
                                            .multiplier(s.getMultiplier())
                                            .startDate(s.getStartDate())
                                            .endDate(s.getEndDate())
                                            .build()
                            ).toList())
                    .build();
            h.setPricing(pricing);
        }
        h.setStatus(r.getStatus());
        h.setFeatured(r.isFeatured());
        h.setOwner(owner);

        // images
        if (r.getImages() != null) {
            List<HotelImage> imgs = r.getImages().stream().map(i ->
                    HotelImage.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).hotel(h).build()
            ).toList();
            h.getImages().addAll(imgs);
        }
        // room types
        if (r.getRoomTypes() != null) {
            List<RoomType> rts = r.getRoomTypes().stream().map(rt ->
                    RoomType.builder()
                            .name(rt.getName())
                            .capacity(rt.getCapacity())
                            .price(rt.getPrice())
                            .amenities(rt.getAmenities())
                            .hotel(h)
                            .build()
            ).toList();
            h.getRoomTypes().addAll(rts);
        }
        return h;
    }

    public HotelResponse toDto(Hotel h) {
        return HotelResponse.builder()
                .id(h.getId())
                .name(h.getName())
                .description(h.getDescription())
                .location(h.getLocation() == null ? null : LocationDto.builder()
                        .address(h.getLocation().getAddress())
                        .city(h.getLocation().getCity())
                        .region(h.getLocation().getRegion())
                        .lat(h.getLocation().getCoordinates() == null ? null : h.getLocation().getCoordinates().getLat())
                        .lng(h.getLocation().getCoordinates() == null ? null : h.getLocation().getCoordinates().getLng())
                        .build())
                .amenities(h.getAmenities())
                .roomsTotal(h.getRoomsTotal())
                .roomsAvailable(h.getRoomsAvailable())
                .pricing(h.getPricing() == null ? null : PricingDto.builder()
                        .basePrice(h.getPricing().getBasePrice())
                        .currency(h.getPricing().getCurrency())
                        .seasonal(h.getPricing().getSeasonal() == null ? List.of() :
                                h.getPricing().getSeasonal().stream().map(s -> SeasonalPriceDto.builder()
                                        .season(s.getSeason())
                                        .multiplier(s.getMultiplier())
                                        .startDate(s.getStartDate())
                                        .endDate(s.getEndDate())
                                        .build()).toList())
                        .build())
                .ratingAverage(h.getRating() == null ? 0.0 : h.getRating().getAverage())
                .ratingCount(h.getRating() == null ? 0L : h.getRating().getCount())
                .images(h.getImages() == null ? List.of() : h.getImages().stream().map(i -> ImageDto.builder()
                        .url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).build()).toList())
                .roomTypes(h.getRoomTypes() == null ? List.of() : h.getRoomTypes().stream().map(rt -> RoomTypeDto.builder()
                        .name(rt.getName()).capacity(rt.getCapacity()).price(rt.getPrice()).amenities(rt.getAmenities()).build()).toList())
                .ownerId(h.getOwner() == null ? null : h.getOwner().getId())
                .status(h.getStatus())
                .bookingsTotal(h.getBookingsTotal())
                .bookingsThisMonth(h.getBookingsThisMonth())
                .featured(h.isFeatured())
                .createdAt(h.getCreatedAt())
                .updatedAt(h.getUpdatedAt())
                .build();
    }
}

