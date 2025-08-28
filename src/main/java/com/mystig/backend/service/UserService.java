package com.mystig.backend.service;

import com.mystig.backend.dto.user.UpdateProfileRequest;
import com.mystig.backend.dto.user.UserResponse;
import com.mystig.backend.model.User;
import com.mystig.backend.model.enums.Role;
import com.mystig.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository users;

    public UserResponse getProfile(String email) {
        User u = users.findByEmail(email).orElseThrow();
        return toResponse(u);
    }

    public UserResponse updateProfile(String email, UpdateProfileRequest r) {
        User u = users.findByEmail(email).orElseThrow();
        if (r.getName() != null) u.setName(r.getName());
        if (r.getPhone() != null) u.setPhone(r.getPhone());
        if (r.getAvatar() != null) u.setAvatar(r.getAvatar());
        if (r.getAddress() != null) u.setAddress(r.getAddress());
        if (r.getBusinessInfo() != null) u.setBusinessInfo(r.getBusinessInfo());
        users.save(u);
        return toResponse(u);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponse> findAll() {
        return users.findAll().stream().map(this::toResponse).toList();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse changeStatus(UUID id, boolean active) {
        User u = users.findById(id).orElseThrow();
        u.setActive(active);
        users.save(u);
        return toResponse(u);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .avatar(u.getAvatar())
                .phone(u.getPhone())
                .address(u.getAddress())
                .businessInfo(u.getBusinessInfo())
                .isActive(u.isActive())
                .emailVerified(u.isEmailVerified())
                .lastLogin(u.getLastLogin())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}

