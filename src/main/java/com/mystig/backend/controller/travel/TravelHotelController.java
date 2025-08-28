package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.Hotel;
import com.mystig.backend.service.travel.TravelHotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/travel/hotels")
@RequiredArgsConstructor
public class TravelHotelController {
    private final TravelHotelService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving hotels: " + e.getMessage());
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> findByCity(@PathVariable UUID cityId) {
        try {
            return ResponseEntity.ok(service.findByCity(cityId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error finding hotels by city: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving hotel: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Hotel hotel) {
        try {
            return ResponseEntity.ok(service.save(hotel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating hotel: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Hotel hotel) {
        try {
            hotel.setId(id);
            return ResponseEntity.ok(service.save(hotel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating hotel: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting hotel: " + e.getMessage());
        }
    }
}

