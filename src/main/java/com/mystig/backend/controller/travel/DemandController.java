package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.Demand;
import com.mystig.backend.service.travel.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/demands")
@RequiredArgsConstructor
public class DemandController {
    private final DemandService service;

    @PatchMapping("/{demandId}/cities/{cityId}/services")
    public ResponseEntity<?> updateServices(
            @PathVariable UUID demandId,
            @PathVariable UUID cityId,
            @RequestBody List<UUID> serviceIds) {
        try {
            return ResponseEntity.ok(service.updateServices(cityId, serviceIds));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating services: " + e.getMessage());
        }
    }

    @PatchMapping("/{demandId}/cities/{cityId}/activities")
    public ResponseEntity<?> updateActivities(
            @PathVariable UUID demandId,
            @PathVariable UUID cityId,
            @RequestBody List<UUID> activityIds) {
        try {
            return ResponseEntity.ok(service.updateActivities(cityId, activityIds));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating activities: " + e.getMessage());
        }
    }

    @PatchMapping("/{demandId}/cities/{cityId}/hotel")
    public ResponseEntity<?> updateHotel(
            @PathVariable UUID demandId,
            @PathVariable UUID cityId,
            @RequestBody UUID hotelId) {
        try {
            return ResponseEntity.ok(service.updateHotel(cityId, hotelId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating hotel: " + e.getMessage());
        }
    }

    @PatchMapping("/{demandId}/cities/{cityId}/transport")
    public ResponseEntity<?> updateTransport(
            @PathVariable UUID demandId,
            @PathVariable UUID cityId,
            @RequestBody UUID transportId) {
        try {
            return ResponseEntity.ok(service.updateTransport(cityId, transportId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating transport: " + e.getMessage());
        }
    }

    @GetMapping("/{demandId}/cities")
    public ResponseEntity<?> getDemandCities(@PathVariable UUID demandId) {
        try {
            return ResponseEntity.ok(service.getDemandCitiesByDemandId(demandId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving demand cities: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving demands: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving demand: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Demand d) {
        try {
            return ResponseEntity.ok(service.save(d));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating demand: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Demand d) {
        try {
            d.setId(id);
            return ResponseEntity.ok(service.save(d));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating demand: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting demand: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<?> calculatePrice(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(service.calculateTotal(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error calculating price: " + e.getMessage());
        }
    }
}