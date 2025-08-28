package com.mystig.backend.service;

import com.mystig.backend.dto.hotel.*;
import com.mystig.backend.mapper.HotelMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.booking.Booking;
import com.mystig.backend.model.enums.BookingType;
import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import com.mystig.backend.model.hotel.Hotel;
import com.mystig.backend.model.hotel.HotelReview;
import com.mystig.backend.repository.BookingRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.repository.hotel.HotelRepository;
import com.mystig.backend.repository.hotel.HotelReviewRepository;
import com.mystig.backend.spec.HotelSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotels;
    private final HotelReviewRepository reviews;
    private final UserRepository users;
    private final BookingRepository bookings;

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public HotelResponse create(HotelRequest req) {
        User owner = users.findById(req.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        Hotel entity = HotelMapper.toEntity(req, owner);
        Hotel saved = hotels.save(entity);
        return HotelMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public HotelResponse update(UUID id, HotelRequest req) {
        Hotel h = hotels.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        // Replace simple fields
        h.setName(req.getName());
        h.setDescription(req.getDescription());
        h.setAmenities(req.getAmenities());
        h.setRoomsTotal(req.getRoomsTotal());
        h.setRoomsAvailable(req.getRoomsAvailable());
        h.setStatus(req.getStatus());
        h.setFeatured(req.isFeatured());

        // Replace location
        Hotel updated = HotelMapper.toEntity(req, h.getOwner());
        h.setLocation(updated.getLocation());
        h.setPricing(updated.getPricing());

        // Replace images & room types
        h.getImages().clear();
        h.getRoomTypes().clear();
        if (updated.getImages() != null) h.getImages().addAll(updated.getImages());
        if (updated.getRoomTypes() != null) h.getRoomTypes().addAll(updated.getRoomTypes());

        Hotel saved = hotels.save(h);
        return HotelMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        hotels.deleteById(id);
    }

    @Transactional
    public HotelResponse get(UUID id) {
        return hotels.findById(id).map(HotelMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
    }

    @Transactional
    public HotelListResponse search(
            String q, String city, String region, Boolean featured, HotelStatus status,
            BigDecimal minPrice, BigDecimal maxPrice, Set<HotelAmenity> amenities,
            int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<Hotel> spec = Specification.where(HotelSpecifications.nameOrDescriptionLike(q))
                .and(HotelSpecifications.cityEquals(city))
                .and(HotelSpecifications.regionEquals(region))
                .and(HotelSpecifications.featuredEq(featured))
                .and(HotelSpecifications.statusEq(status))
                .and(HotelSpecifications.basePriceBetween(minPrice, maxPrice))
                .and(HotelSpecifications.hasAmenitiesAll(amenities));

        Page<Hotel> result = hotels.findAll(spec, pageable);
        List<HotelResponse> items = result.getContent().stream().map(HotelMapper::toDto).toList();
        return HotelListResponse.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(items)
                .build();
    }

    private Sort toSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        // format: field,asc|desc;field2,asc
        List<Sort.Order> orders = Arrays.stream(sort.split(";"))
                .map(s -> s.split(","))
                .map(a -> a.length == 2
                        ? new Sort.Order("asc".equalsIgnoreCase(a[1]) ? Sort.Direction.ASC : Sort.Direction.DESC, a[0])
                        : new Sort.Order(Sort.Direction.ASC, a[0]))
                .toList();
        return Sort.by(orders);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ReviewResponse addReview(UUID hotelId, UUID userId, ReviewRequest req) {
        Hotel h = hotels.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        User u = (userId == null)
                ? users.findByEmail(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"))
                : users.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        HotelReview r = HotelReview.builder()
                .hotel(h)
                .user(u)
                .rating(req.getRating())
                .comment(req.getComment())
                .build();
        HotelReview saved = reviews.save(r);

        // recalc rating
        h.getReviews().add(saved);
        h.recalcRating();

        return ReviewResponse.builder()
                .id(saved.getId())
                .userId(u.getId())
                .userName(u.getName())
                .rating(saved.getRating())
                .comment(saved.getComment())
                .date(saved.getDate())
                .build();
    }

    // @Transactional
    // public List<BookingSummaryDto> bookingsForHotel(UUID hotelId) {
    //     Pageable pageable = PageRequest.of(0, 10);
    //     List<Booking> list = bookings.findByBookingTypeAndItemId(BookingType.HOTEL, hotelId, pageable);
    //     return list.stream().map(b -> BookingSummaryDto.builder()
    //             .id(b.getId())
    //             .bookingNumber(b.getBookingNumber())
    //             .name(b.getName())
    //             .participants(b.getParticipants())
    //             .totalAmount(b.getTotalAmount())
    //             .currency(b.getCurrency())
    //             .createdAt(b.getCreatedAt())
    //             .build()).toList();
    // }

    // Optionally update hotel counters (call when booking created in the future Bookings module)
    @Transactional
    public void updateBookingCounters(UUID hotelId) {
        Hotel h = hotels.findById(hotelId).orElseThrow();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Booking> page = bookings.findByBookingTypeAndItemId(BookingType.HOTEL, hotelId, pageable);

        List<Booking> list = page.getContent(); // <-- FIX HERE
        long total = page.getTotalElements();   // use total elements from Page

        YearMonth thisMonth = YearMonth.now();
        long thisMonthCount = list.stream().filter(b -> {
            YearMonth ym = YearMonth.from(b.getCreatedAt().atZone(java.time.ZoneOffset.UTC));
            return ym.equals(thisMonth);
        }).count();

        h.setBookingsTotal(total);
        h.setBookingsThisMonth(thisMonthCount);
    }

}

