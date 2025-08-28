package com.mystig.backend.spec;

import com.mystig.backend.model.enums.HotelAmenity;
import com.mystig.backend.model.enums.HotelStatus;
import com.mystig.backend.model.hotel.Hotel;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Set;

public class HotelSpecifications {

    public static Specification<Hotel> nameOrDescriptionLike(String q) {
        return (root, cq, cb) -> (q == null || q.isBlank())
                ? cb.conjunction()
                : cb.or(
                cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("description")), "%" + q.toLowerCase() + "%")
        );
    }

    public static Specification<Hotel> cityEquals(String city) {
        return (root, cq, cb) -> (city == null || city.isBlank())
                ? cb.conjunction()
                : cb.equal(root.get("location").get("city"), city);
    }

    public static Specification<Hotel> regionEquals(String region) {
        return (root, cq, cb) -> (region == null || region.isBlank())
                ? cb.conjunction()
                : cb.equal(root.get("location").get("region"), region);
    }

    public static Specification<Hotel> featuredEq(Boolean featured) {
        return (root, cq, cb) -> (featured == null)
                ? cb.conjunction()
                : (featured ? cb.isTrue(root.get("featured")) : cb.isFalse(root.get("featured")));
    }

    public static Specification<Hotel> statusEq(HotelStatus status) {
        return (root, cq, cb) -> (status == null) ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    // price filter on pricing.basePrice
    public static Specification<Hotel> basePriceBetween(BigDecimal min, BigDecimal max) {
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


    // amenities contains ALL provided
    public static Specification<Hotel> hasAmenitiesAll(Set<HotelAmenity> amenities) {
        return (root, cq, cb) -> {
            if (amenities == null || amenities.isEmpty()) return cb.conjunction();
            Join<Object, Object> join = root.join("amenities");
            cq.distinct(true);
            return join.in(amenities);
        };
    }
}
