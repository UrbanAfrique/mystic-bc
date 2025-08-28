package com.mystig.backend.controller;

import com.mystig.backend.dto.hotel.*;
import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import com.mystig.backend.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotels;

    // GET /api/hotels?city=&region=&q=&featured=&status=&minPrice=&maxPrice=&amenities=WIFI,SPA&sort=name,asc;createdAt,desc&page=0&size=20
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) HotelStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Set<HotelAmenity> amenities,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        try {
            return ResponseEntity.ok(hotels.search(q, city, region, featured, status, minPrice, maxPrice, amenities, page, size, sort));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(hotels.get(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> create(@RequestBody HotelRequest req) {
        try {
            return ResponseEntity.ok(hotels.create(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody HotelRequest req) {
        try {
            return ResponseEntity.ok(hotels.update(id, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            hotels.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/reviews")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> review(@PathVariable UUID id,
                                    @RequestBody ReviewRequest req,
                                    @AuthenticationPrincipal UserDetails principal) {
        try {
            UUID userId = resolveUserId(principal);
            return ResponseEntity.ok(hotels.addReview(id, userId, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @GetMapping("/{id}/bookings")
    // public ResponseEntity<List<BookingSummaryDto>> bookings(@PathVariable UUID id) {
    //     return ResponseEntity.ok(hotels.bookingsForHotel(id));
    // }

    private UUID resolveUserId(UserDetails principal) {
        // In your current setup, you have only email in the principal.
        // To map -> userId, either:
        // 1) Change CustomUserDetails to carry userId, OR
        // 2) Resolve inside service by email. For now, service method takes userId,
        //    but we can adapt. To keep code compiling, return null here; service ignores and resolves by email if null.
        return null;
    }
}