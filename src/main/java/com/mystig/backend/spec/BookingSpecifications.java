package com.mystig.backend.spec;

import com.mystig.backend.model.booking.Booking;
import com.mystig.backend.model.enums.BookingStatus;
import com.mystig.backend.model.enums.BookingType;
import com.mystig.backend.model.enums.PaymentStatus;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

public class BookingSpecifications {

    public static Specification<Booking> bookingTypeEq(BookingType t) {
        return (root, cq, cb) -> t == null ? cb.conjunction() : cb.equal(root.get("bookingType"), t);
    }

    public static Specification<Booking> itemIdEq(UUID id) {
        return (root, cq, cb) -> id == null ? cb.conjunction() : cb.equal(root.get("itemId"), id);
    }

    public static Specification<Booking> statusEq(BookingStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("status"), s);
    }

    public static Specification<Booking> paymentStatusEq(PaymentStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("paymentStatus"), s);
    }

    public static Specification<Booking> sellerIdEq(UUID sellerId) {
        return (root, cq, cb) -> {
            if (sellerId == null) return cb.conjunction();
            Path<Object> seller = root.get("seller").get("id");
            return cb.equal(seller, sellerId);
        };
    }

    public static Specification<Booking> createdBetween(Instant from, Instant to) {
        return (root, cq, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from != null && to != null) return cb.between(root.get("createdAt"), from, to);
            if (from != null) return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Booking> qLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("bookingNumber")), like),
                    cb.like(cb.lower(root.get("customer").get("name")), like),
                    cb.like(cb.lower(root.get("customer").get("email")), like),
                    cb.like(cb.lower(root.get("itemDetails").get("name")), like)
            );
        };
    }
}

