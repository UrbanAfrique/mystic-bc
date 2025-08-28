package com.mystig.backend.controller;

import com.mystig.backend.dto.auth.*;
import com.mystig.backend.model.User;
import com.mystig.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        auth.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(auth.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest req) {
        auth.logout(req.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshTokenRequest req) {
        return ResponseEntity.ok(auth.refresh(req));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestBody ForgotPasswordRequest req) {
        auth.forgotPassword(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> reset(@RequestBody ResetPasswordRequest req) {
        auth.resetPassword(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
        return ResponseEntity.ok(auth.verifyEmail(token));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@RequestHeader("Authorization") String authHeader) {
        // The JwtAuthenticationFilter already authenticates; simply return principal email
        // For simplicity, we parse the header here (controller can be improved to use Principal)
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return ResponseEntity.ok(auth.me(email));
    }
}
