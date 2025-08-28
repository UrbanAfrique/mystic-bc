package com.mystig.backend.spec;

import com.mystig.backend.model.enums.PackageStatus;
import com.mystig.backend.model.packagepkg.TravelPackage;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PackageSpecifications {

    public static Specification<TravelPackage> nameOrDescriptionLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }

    public static Specification<TravelPackage> typeEq(com.mystig.backend.model.enums.PackageType t) {
        return (root, cq, cb) -> t == null ? cb.conjunction() : cb.equal(root.get("type"), t);
    }

    public static Specification<TravelPackage> statusEq(PackageStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("status"), s);
    }

    public static Specification<TravelPackage> basePriceBetween(BigDecimal min, BigDecimal max) {
        return (root, cq, cb) -> {
            if (min == null && max == null) return cb.conjunction();

            Path<BigDecimal> pricePath = root.get("pricing").get("basePrice");

            if (min != null && max != null) {
                return cb.between(pricePath, min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(pricePath, min);
            }
            return cb.lessThanOrEqualTo(pricePath, max);
        };
    }

}
