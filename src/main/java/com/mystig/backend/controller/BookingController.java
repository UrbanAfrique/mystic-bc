package com.mystig.backend.controller;

import com.mystig.backend.dto.booking.*;
import com.mystig.backend.model.enums.*;
import com.mystig.backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // GET /api/bookings?q=&type=&itemId=&status=&paymentStatus=&sellerId=&from=&to=&page=0&size=20&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) BookingType type,
            @RequestParam(required = false) UUID itemId,
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) PaymentStatus paymentStatus,
            @RequestParam(required = false) UUID sellerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        try {
            return ResponseEntity.ok(bookingService.search(q, type, itemId, status, paymentStatus, sellerId, from, to, page, size, sort));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(bookingService.get(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> create(@RequestBody BookingRequest req) {
        try {
            return ResponseEntity.ok(bookingService.create(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id,
                                          @RequestBody UpdateBookingStatusRequest req) {
        try {
            return ResponseEntity.ok(bookingService.updateStatus(id, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            bookingService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}