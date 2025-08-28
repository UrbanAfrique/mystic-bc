package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.ServiceOffering;
import com.mystig.backend.service.travel.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceOfferingController {
    private final ServiceOfferingService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving services: " + e.getMessage());
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> findByCity(@PathVariable UUID cityId) {
        try {
            return ResponseEntity.ok(service.findByCity(cityId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error finding services by city: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving service: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServiceOffering s) {
        try {
            return ResponseEntity.ok(service.save(s));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating service: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ServiceOffering s) {
        try {
            s.setId(id);
            return ResponseEntity.ok(service.save(s));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating service: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting service: " + e.getMessage());
        }
    }
}