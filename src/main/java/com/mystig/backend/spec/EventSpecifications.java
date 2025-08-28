package com.mystig.backend.spec;

import com.mystig.backend.model.event.Event;
import com.mystig.backend.model.enums.EventStatus;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> search(
            String q,
            String city,
            LocalDate from,
            LocalDate to,
            EventStatus status,
            List<String> tags
    ) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();
            if (q != null && !q.isEmpty()) {
                predicates = cb.and(predicates, cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + q.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + q.toLowerCase() + "%")
                ));
            }
            if (city != null && !city.isEmpty()) {
                predicates = cb.and(predicates, cb.equal(root.get("city"), city));
            }
            if (status != null) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), status));
            }
             if (from != null) {
                predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("dateRange").get("start"), from));
            }
            if (to != null) {
                predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("dateRange").get("end"), to));
            }
            if (tags != null && !tags.isEmpty()) {
                predicates = cb.and(predicates, root.join("tags").in(tags));
            }
            return predicates;
        };
    }


    public static Specification<Event> titleOrDescriptionLike(String q) {
        return (root, cq, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }

    public static Specification<Event> cityEquals(String city) {
        return (root, cq, cb) -> (city == null || city.isBlank())
                ? cb.conjunction()
                : cb.equal(root.get("location").get("city"), city);
    }

    public static Specification<Event> dateBetween(LocalDate from, LocalDate to) {
        return (root, cq, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from != null && to != null)
                return cb.and(cb.greaterThanOrEqualTo(root.get("dateRange").get("start"), from),
                        cb.lessThanOrEqualTo(root.get("dateRange").get("end"), to));
            if (from != null) return cb.greaterThanOrEqualTo(root.get("dateRange").get("start"), from);
            return cb.lessThanOrEqualTo(root.get("dateRange").get("end"), to);
        };
    }

    public static Specification<Event> statusEq(EventStatus s) {
        return (root, cq, cb) -> s == null ? cb.conjunction() : cb.equal(root.get("status"), s);
    }

    public static Specification<Event> tagsIn(Set<String> tags) {
        return (root, cq, cb) -> {
            if (tags == null || tags.isEmpty()) return cb.conjunction();
            Join<Object, Object> join = root.join("tags");
            cq.distinct(true);
            return join.in(tags);
        };
    }
}

