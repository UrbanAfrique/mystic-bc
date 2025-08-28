package com.mystig.backend.controller;

import com.mystig.backend.dto.event.*;
import com.mystig.backend.model.User;
import com.mystig.backend.service.EventService;
import com.mystig.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService events;
    private final UserRepository users;

    // GET /api/events?q=&city=&from=&to=&status=&tags=tag1,tag2&page=0&size=20&sort=createdAt,desc
    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) com.mystig.backend.model.enums.EventStatus status,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort
    ) {
        try {
            return ResponseEntity.ok(events.search(q, city, from, to, status, tags, page, size, sort));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(events.get(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EventRequestDto req) {
        try {
            return ResponseEntity.ok(events.create(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody EventRequestDto req) {
        try {
            return ResponseEntity.ok(events.update(id, req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            events.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/tickets/purchase")
    public ResponseEntity<?> purchase(
            @PathVariable UUID id,
            @RequestBody PurchaseTicketRequest req,
            Authentication authentication) {
        try {
            User buyer = null;
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                buyer = users.findByEmail(email).orElse(null);
            }
            AttendeeResponseDto a = events.purchaseTicket(id, req, buyer);
            return ResponseEntity.ok(a);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/attendees")
    public ResponseEntity<?> attendees(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(events.attendeesForEvent(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}