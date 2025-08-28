package com.mystig.backend.controller.travel;

import org.springframework.security.access.prepost.PreAuthorize;
import com.mystig.backend.model.travel.Transports;
import com.mystig.backend.service.travel.TravelTransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel/transports")
@RequiredArgsConstructor
public class TravelTransportController {
    private final TravelTransportService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving transports: " + e.getMessage());
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> findByCity(@PathVariable UUID cityId) {
        try {
            return ResponseEntity.ok(service.findByCity(cityId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error finding transports by city: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving transport: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Transports t) {
        try {
            return ResponseEntity.ok(service.save(t));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating transport: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Transports t) {
        try {
            t.setId(id);
            return ResponseEntity.ok(service.save(t));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating transport: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting transport: " + e.getMessage());
        }
    }
}