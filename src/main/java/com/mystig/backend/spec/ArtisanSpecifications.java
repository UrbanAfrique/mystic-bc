package com.mystig.backend.spec;

import com.mystig.backend.model.artisan.ArtisanProduct;
import com.mystig.backend.model.enums.ArtisanCategory;
import com.mystig.backend.model.enums.ArtisanStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public class ArtisanSpecifications {

    public static Specification<ArtisanProduct> qLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like),
                    cb.like(cb.lower(root.get("origin")), like),
                    cb.like(cb.lower(root.get("craftsman")), like)
            );
        };
    }

    public static Specification<ArtisanProduct> categoryEq(ArtisanCategory cat) {
        return (root, cq, cb) -> cat == null ? cb.conjunction() : cb.equal(root.get("category"), cat);
    }

    public static Specification<ArtisanProduct> statusEq(ArtisanStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("status"), s);
    }

    public static Specification<ArtisanProduct> ownerEq(UUID ownerId) {
        return (root, cq, cb) -> ownerId == null ? cb.conjunction() : cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<ArtisanProduct> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, cq, cb) -> {
            if (min == null && max == null) return cb.conjunction();

            Path<BigDecimal> pricePath = root.get("price");
            if (min != null && max != null) {
                return cb.between(pricePath, min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(pricePath, min);
            }
            return cb.lessThanOrEqualTo(pricePath, max);
        };
    }

    public static Specification<ArtisanProduct> hasMaterial(String material) {
        return (root, cq, cb) -> {
            if (material == null || material.isBlank()) return cb.conjunction();
            Join<Object, Object> join = root.join("materials");
            cq.distinct(true);
            return cb.equal(cb.lower(join.as(String.class)), material.toLowerCase());
        };
    }
}
