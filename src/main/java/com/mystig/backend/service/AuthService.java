package com.mystig.backend.service;

import com.mystig.backend.dto.auth.*;
import com.mystig.backend.model.*;
import com.mystig.backend.model.enums.Role;
import com.mystig.backend.repository.*;
import com.mystig.backend.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordResetTokenRepository resetRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;
    private final TokenService tokenService;

    private static final long REFRESH_TTL_DAYS = 30;
    private static final long RESET_TTL_MINUTES = 30;

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User u = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .role(Role.ROLE_SELLER)
                .avatar(req.getAvatar())
                .phone(req.getPhone())
                .address(req.getAddress())
                .businessInfo(req.getBusinessInfo())
                .isActive(true)
                .emailVerified(true) // ✅ auto-verified
                .build();
        userRepo.save(u);
    }

    @Transactional
    public TokenResponse login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        User u = userRepo.findByEmail(req.getEmail()).orElseThrow();
        u.setLastLogin(Instant.now());

        String access = jwt.generateAccessToken(u.getEmail(), Map.of(
                "role", u.getRole().name(),
                "uid", u.getId().toString()
        ));
        String refreshRaw = tokenService.generateToken(128);
        RefreshToken rt = RefreshToken.builder()
                .token(refreshRaw)
                .user(u)
                .expiresAt(Instant.now().plus(REFRESH_TTL_DAYS, ChronoUnit.DAYS))
                .revoked(false)
                .used(false)
                .createdAt(Instant.now())
                .build();
        refreshRepo.save(rt);

        return new TokenResponse(access, refreshRaw, "Bearer");
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshRepo.findByToken(refreshToken).ifPresent(rt -> {
            rt.setRevoked(true);
            rt.setUsed(true);
            refreshRepo.save(rt);
        });
    }

    @Transactional
    public TokenResponse refresh(RefreshTokenRequest req) {
        RefreshToken rt = refreshRepo.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.isRevoked() || rt.isUsed() || rt.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token expired or revoked");
        }
        User u = rt.getUser();

        // rotate token (best practice)
        rt.setUsed(true);
        rt.setRevoked(true);
        refreshRepo.save(rt);

        String newAccess = jwt.generateAccessToken(u.getEmail(),
                Map.of("role", u.getRole().name(), "uid", u.getId().toString()));
        String newRefreshRaw = tokenService.generateToken(128);
        RefreshToken newRt = RefreshToken.builder()
                .token(newRefreshRaw)
                .user(u)
                .expiresAt(Instant.now().plus(REFRESH_TTL_DAYS, ChronoUnit.DAYS))
                .revoked(false)
                .used(false)
                .createdAt(Instant.now())
                .build();
        refreshRepo.save(newRt);

        return new TokenResponse(newAccess, newRefreshRaw, "Bearer");
    }

    /**
     * Stubbed method – always return success since email verification is disabled.
     */
    @Transactional
    public String verifyEmail(String token) {
        return "Email verification is disabled – all users are auto-verified.";
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest req) {
        User u = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        String token = tokenService.generateToken(64);
        PasswordResetToken prt = PasswordResetToken.builder()
                .token(token)
                .user(u)
                .expiresAt(Instant.now().plus(RESET_TTL_MINUTES, ChronoUnit.MINUTES))
                .createdAt(Instant.now())
                .used(false)
                .build();
        resetRepo.save(prt);

        // ❌ Email sending removed
        System.out.println("Password reset token (for testing): " + token);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest req) {
        PasswordResetToken prt = resetRepo.findByToken(req.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (prt.isUsed() || prt.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expired or already used");
        }
        User u = prt.getUser();
        u.setPassword(encoder.encode(req.getNewPassword()));
        prt.setUsed(true);
    }

    public User me(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }
}
