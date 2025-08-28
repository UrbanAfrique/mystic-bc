package com.mystig.backend.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    // URL-safe random token
    public String generateToken(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}

