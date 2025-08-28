package com.mystig.backend.spec;

import com.mystig.backend.model.enums.DifficultyLevel;
import com.mystig.backend.model.enums.FoodStatus;
import com.mystig.backend.model.enums.FoodType;
import com.mystig.backend.model.food.FoodExperience;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;

import java.math.BigDecimal;
import java.util.UUID;

public class FoodSpecifications {

    public static Specification<FoodExperience> qLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like),
                    cb.like(cb.lower(root.get("location")), like)
            );
        };
    }

    public static Specification<FoodExperience> typeEq(FoodType t) {
        return (root, cq, cb) -> t == null ? cb.conjunction() : cb.equal(root.get("type"), t);
    }

    public static Specification<FoodExperience> difficultyEq(DifficultyLevel d) {
        return (root, cq, cb) -> d == null ? cb.conjunction() : cb.equal(root.get("difficulty"), d);
    }

    public static Specification<FoodExperience> statusEq(FoodStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("status"), s);
    }

    public static Specification<FoodExperience> ownerEq(UUID ownerId) {
        return (root, cq, cb) -> ownerId == null ? cb.conjunction() : cb.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<FoodExperience> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, cq, cb) -> {
            if (min == null && max == null) return cb.conjunction();

            // Explicitly define the path type
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

    public static Specification<FoodExperience> fitsParticipants(Integer participants) {
        return (root, cq, cb) -> {
            if (participants == null) return cb.conjunction();

            Path<Integer> participantsPath = root.get("maxParticipants");
            return cb.greaterThanOrEqualTo(participantsPath, participants);
        };
    }
}
