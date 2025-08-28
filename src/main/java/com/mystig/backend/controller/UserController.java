package com.mystig.backend.controller;

import com.mystig.backend.dto.user.ChangeStatusRequest;
import com.mystig.backend.dto.user.UpdateProfileRequest;
import com.mystig.backend.dto.user.UserResponse;
import com.mystig.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService users;

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal UserDetails principal) {
        try {
            return ResponseEntity.ok(users.getProfile(principal.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody UpdateProfileRequest req) {
        try {
            return ResponseEntity.ok(users.updateProfile(principal.getUsername(), req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(users.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id,
                                          @RequestBody ChangeStatusRequest req) {
        try {
            return ResponseEntity.ok(users.changeStatus(id, req.isActive()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}