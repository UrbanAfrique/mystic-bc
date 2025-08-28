package com.mystig.backend.spec;

import com.mystig.backend.model.enums.TransportStatus;
import com.mystig.backend.model.enums.TransportType;
import com.mystig.backend.model.transport.Transport;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.UUID;

public class TransportSpecifications {

    public static Specification<Transport> qLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }

    public static Specification<Transport> typeEq(TransportType type) {
        return (root, cq, cb) -> type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    public static Specification<Transport> statusEq(TransportStatus status) {
        return (root, cq, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Transport> ownerEq(UUID ownerId) {
        return (root, cq, cb) -> ownerId == null ? cb.conjunction() : cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<Transport> priceBetween(BigDecimal min, BigDecimal max) {
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

    

    // filter by availability city (checks cities collection contains the value)
    public static Specification<Transport> availableInCity(String city) {
        return (root, cq, cb) -> {
            if (city == null || city.isBlank()) return cb.conjunction();
            Join<Object, Object> join = root.join("availability").join("cities");
            cq.distinct(true);
            return cb.equal(cb.lower(join.as(String.class)), city.toLowerCase());
        };
    }

    // filter by feature (any of features)
    public static Specification<Transport> hasFeature(String feature) {
        return (root, cq, cb) -> {
            if (feature == null || feature.isBlank()) return cb.conjunction();
            Join<Object, Object> join = root.join("features");
            cq.distinct(true);
            return cb.equal(cb.lower(join.as(String.class)), feature.toLowerCase());
        };
    }
}
