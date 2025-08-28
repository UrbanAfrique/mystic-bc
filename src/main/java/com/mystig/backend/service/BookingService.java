package com.mystig.backend.service;

import com.mystig.backend.dto.booking.*;
import com.mystig.backend.mapper.BookingMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.booking.Booking;
import com.mystig.backend.model.enums.*;
import com.mystig.backend.repository.BookingRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.BookingSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookings;
    private final UserRepository users;
    private final HotelService hotelService; // to update hotel counters

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public BookingResponse create(BookingRequest req) {
        User seller = users.findById(req.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));

        String number = generateBookingNumber();
        Booking b = BookingMapper.toEntity(req, seller, number);
        // default states
        b.setStatus(BookingStatus.PENDING);
        b.setPaymentStatus(PaymentStatus.PENDING);

        Booking saved = bookings.save(b);

        // update hotel counters if needed
        if (saved.getBookingType() == BookingType.HOTEL) {
            hotelService.updateBookingCounters(saved.getItemId());
        }
        return BookingMapper.toDto(saved);
    }

    @Transactional
    public BookingResponse get(UUID id) {
        return bookings.findById(id).map(BookingMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    @Transactional
    public BookingListResponse search(
            String q, BookingType type, UUID itemId, BookingStatus status, PaymentStatus paymentStatus,
            UUID sellerId, Instant from, Instant to, int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<Booking> spec = Specification.where(BookingSpecifications.qLike(q))
                .and(BookingSpecifications.bookingTypeEq(type))
                .and(BookingSpecifications.itemIdEq(itemId))
                .and(BookingSpecifications.statusEq(status))
                .and(BookingSpecifications.paymentStatusEq(paymentStatus))
                .and(BookingSpecifications.sellerIdEq(sellerId))
                .and(BookingSpecifications.createdBetween(from, to));

        Page<Booking> result = bookings.findAll(spec, pageable);
        return BookingListResponse.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(result.getContent().stream().map(BookingMapper::toDto).toList())
                .build();
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public BookingResponse updateStatus(UUID id, UpdateBookingStatusRequest req) {
        Booking b = bookings.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        if (req.getStatus() != null) b.setStatus(req.getStatus());
        if (req.getPaymentStatus() != null) b.setPaymentStatus(req.getPaymentStatus());
        Booking saved = bookings.save(b);

        if (saved.getBookingType() == BookingType.HOTEL) {
            hotelService.updateBookingCounters(saved.getItemId());
        }
        return BookingMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        Booking b = bookings.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        UUID hotelId = (b.getBookingType() == BookingType.HOTEL) ? b.getItemId() : null;
        bookings.delete(b);
        if (hotelId != null) hotelService.updateBookingCounters(hotelId);
    }

    private Sort toSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        List<Sort.Order> orders = Arrays.stream(sort.split(";"))
                .map(s -> s.split(","))
                .map(a -> a.length == 2
                        ? new Sort.Order("asc".equalsIgnoreCase(a[1]) ? Sort.Direction.ASC : Sort.Direction.DESC, a[0])
                        : new Sort.Order(Sort.Direction.ASC, a[0]))
                .toList();
        return Sort.by(orders);
    }

    private String generateBookingNumber() {
        // BK-YYYY-<random 6 alphanum>
        String year = String.valueOf(Year.now().getValue());
        String rand = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return "BK-" + year + "-" + rand;
    }
}

