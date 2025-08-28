package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.SpecialPackage;
import com.mystig.backend.repository.travel.SpecialPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialPackageService {
    private final SpecialPackageRepository repo;

    public List<SpecialPackage> findAll() {
        return repo.findAll();
    }

    public SpecialPackage findById(UUID id) {
        return repo.findById(id).orElseThrow();
    }

    public SpecialPackage save(SpecialPackage sp) {
        return repo.save(sp);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    public BigDecimal calculateDiscountedPrice(SpecialPackage sp) {
        if (sp.getDiscountPercent() == null) {
            return BigDecimal.valueOf(sp.getBasePrice());
        }

        // Convert both basePrice and discountPercent to BigDecimal
        BigDecimal basePrice = BigDecimal.valueOf(sp.getBasePrice());
        BigDecimal discountPercent = BigDecimal.valueOf(sp.getDiscountPercent());

        // Calculate discount amount: basePrice * (discountPercent / 100)
        BigDecimal discountFactor = discountPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal discountAmount = basePrice.multiply(discountFactor);

        // Return final price: basePrice - discountAmount
        return basePrice.subtract(discountAmount);
    }
}