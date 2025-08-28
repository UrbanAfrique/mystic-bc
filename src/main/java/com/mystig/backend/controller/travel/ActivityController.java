package com.mystig.backend.controller.travel;

import com.mystig.backend.model.travel.Activity;
import com.mystig.backend.service.travel.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving activities: " + e.getMessage());
        }
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<?> findByCity(@PathVariable UUID cityId) {
        try {
            return ResponseEntity.ok(service.findByCity(cityId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving activities by city: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Activity a) {
        try {
            return ResponseEntity.ok(service.save(a));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating activity: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Activity a) {
        try {
            a.setId(id);
            return ResponseEntity.ok(service.save(a));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating activity: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting activity: " + e.getMessage());
        }
    }
}