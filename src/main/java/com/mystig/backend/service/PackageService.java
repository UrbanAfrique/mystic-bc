package com.mystig.backend.service;

import com.mystig.backend.dto.booking.BookingRequest;
import com.mystig.backend.dto.booking.ItemDetailsDto;
import com.mystig.backend.dto.booking.CustomerDto;
import com.mystig.backend.dto.packagepkg.*;
import com.mystig.backend.mapper.PackageMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.packagepkg.TravelPackage;
import com.mystig.backend.repository.packagepkg.PackageReviewRepository;
import com.mystig.backend.repository.packagepkg.TravelPackageRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.PackageSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final TravelPackageRepository packages;
    private final PackageReviewRepository reviews;
    private final UserRepository users;
    private final BookingService bookingService; // dependency on previously created BookingService

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public PackageResponseDto create(PackageRequestDto req) {
        User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        TravelPackage p = PackageMapper.toEntity(req, owner);
        TravelPackage saved = packages.save(p);
        return PackageMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public PackageResponseDto update(UUID id, PackageRequestDto req) {
        TravelPackage existing = packages.findById(id).orElseThrow(() -> new EntityNotFoundException("Package not found"));
        User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
        TravelPackage updated = PackageMapper.toEntity(req, owner);
        // keep id and replace children
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        TravelPackage saved = packages.save(updated);
        return PackageMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        packages.deleteById(id);
    }

    @Transactional
    public PackageResponseDto get(UUID id) {
        return packages.findById(id).map(PackageMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Package not found"));
    }

    @Transactional
    public PackageListResponseDto search(String q, com.mystig.backend.model.enums.PackageType type,
                                         com.mystig.backend.model.enums.PackageStatus status,
                                         BigDecimal minPrice, BigDecimal maxPrice,
                                         int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<TravelPackage> spec = Specification.where(PackageSpecifications.nameOrDescriptionLike(q))
                .and(PackageSpecifications.typeEq(type))
                .and(PackageSpecifications.statusEq(status))
                .and(PackageSpecifications.basePriceBetween(minPrice, maxPrice));

        Page<TravelPackage> result = packages.findAll(spec, pageable);
        List<PackageResponseDto> items = result.getContent().stream().map(PackageMapper::toDto).toList();
        return PackageListResponseDto.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(items)
                .build();
    }

    private Sort toSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        List<Sort.Order> orders = Arrays.stream(sort.split(";"))
                .map(s -> s.split(","))
                .map(a -> a.length == 2 ? new Sort.Order("asc".equalsIgnoreCase(a[1]) ? Sort.Direction.ASC : Sort.Direction.DESC, a[0])
                        : new Sort.Order(Sort.Direction.ASC, a[0])).toList();
        return Sort.by(orders);
    }

    /**
     * Book this package: creates a booking via BookingService.
     * The method constructs BookingRequest and forwards.
     */
    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public com.mystig.backend.dto.booking.BookingResponse bookPackage(UUID packageId, com.mystig.backend.dto.packagepkg.BookingPackageRequestDto req) {
        TravelPackage p = packages.findById(packageId).orElseThrow(() -> new EntityNotFoundException("Package not found"));

        // Build booking request (uses BookingRequest from booking module)
        ItemDetailsDto item = ItemDetailsDto.builder()
                .name(p.getName())
                .description(p.getDescription())
                .startDate(null) // packages may have availability dates; set if present
                .endDate(null)
                .build();

        CustomerDto customer = CustomerDto.builder()
                .name(req.getCustomerName())
                .email(req.getCustomerEmail())
                .phone(req.getCustomerPhone())
                .street(req.getCustomerStreet())
                .city(req.getCustomerCity())
                .country(req.getCustomerCountry())
                .zipCode(req.getCustomerZip())
                .build();

        BookingRequest br = BookingRequest.builder()
                .customer(customer)
                .bookingType(com.mystig.backend.model.enums.BookingType.PACKAGE)
                .itemId(p.getId())
                .itemDetails(item)
                .participants(req.getParticipants())
                .totalAmount(req.getTotalAmount())
                .currency(req.getCurrency() == null ? "MAD" : req.getCurrency())
                .paymentMethod(req.getPaymentMethod())
                .specialRequests(req.getSpecialRequests())
                .sellerId(p.getOwner() == null ? null : p.getOwner().getId())
                .build();

        // delegate to BookingService (this will update package counters if BookingService handles PACKAGE type)
        return bookingService.create(br);
    }

    // update package counters when booking changes (if needed). Similar to HotelService, implement if you track counts
    @Transactional
    public void updateBookingCounters(UUID packageId) {
        TravelPackage p = packages.findById(packageId).orElseThrow();
        long total = 0L; // compute via booking repository when needed
        p.setBookingsTotal(total);
        packages.save(p);
    }
}

