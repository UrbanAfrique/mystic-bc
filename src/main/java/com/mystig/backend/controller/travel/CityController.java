package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.City;
import com.mystig.backend.repository.travel.CityRepository;
import com.mystig.backend.service.travel.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService service;
    private final CityRepository cityRepo;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving cities: " + e.getMessage());
        }
    }

    @GetMapping("/paginate")
    public ResponseEntity<?> getAllCities(Pageable pageable) {
        try {
            return ResponseEntity.ok(cityRepo.findAll(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving paginated cities: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving city: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody City city) {
        try {
            return ResponseEntity.ok(service.save(city));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating city: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody City city) {
        try {
            city.setId(id);
            return ResponseEntity.ok(service.save(city));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating city: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting city: " + e.getMessage());
        }
    }
}