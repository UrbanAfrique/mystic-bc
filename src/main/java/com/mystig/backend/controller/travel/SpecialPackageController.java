package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.SpecialPackage;
import com.mystig.backend.service.travel.SpecialPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/travel/packages")
@RequiredArgsConstructor
public class SpecialPackageController {
    private final SpecialPackageService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving packages: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            SpecialPackage specialPackage = service.findById(id);
            return ResponseEntity.ok(specialPackage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving package: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SpecialPackage sp) {
        try {
            return ResponseEntity.ok(service.save(sp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating package: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody SpecialPackage sp) {
        try {
            sp.setId(id);
            return ResponseEntity.ok(service.save(sp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating package: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting package: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<?> getDiscountedPrice(@PathVariable UUID id) {
        try {
            SpecialPackage sp = service.findById(id);
            BigDecimal discountedPrice = service.calculateDiscountedPrice(sp);
            return ResponseEntity.ok(discountedPrice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error calculating discounted price: " + e.getMessage());
        }
    }
}