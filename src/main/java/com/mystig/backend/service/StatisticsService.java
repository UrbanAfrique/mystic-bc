package com.mystig.backend.service;
import com.mystig.backend.dto.statistics.BookingTrendDto;
import com.mystig.backend.dto.statistics.*;
import com.mystig.backend.model.enums.BookingType;
import com.mystig.backend.model.hotel.Hotel;
import com.mystig.backend.model.packagepkg.TravelPackage;
import com.mystig.backend.model.event.Event;
import com.mystig.backend.repository.BookingRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.repository.hotel.HotelRepository;
import com.mystig.backend.repository.packagepkg.TravelPackageRepository;
import com.mystig.backend.repository.event.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.Arrays;
import java.nio.ByteBuffer;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final TravelPackageRepository packageRepository;
    private final EventRepository eventRepository;

    @PersistenceContext
    private final EntityManager em;

    /**
     * Revenue analytics by period: "day", "month", "year".
     * from/to are optional ISO dates (yyyy-MM-dd). If null, uses last 30 days for day; last 12 months for month; last 5 years for year.
     */
    @Transactional(readOnly = true)
    public List<RevenueDto> revenue(String period, LocalDate from, LocalDate to) {

        if (period == null || period.isBlank()) period = "day";
        String sql;
        // Assuming MySQL: DATE(), DATE_FORMAT()
        switch (period.toLowerCase()) {
            case "month":
                // format YYYY-MM
                sql = "SELECT DATE_FORMAT(b.created_at, '%Y-%m') AS period, COALESCE(SUM(b.total_amount),0) AS amt " +
                        "FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE_FORMAT(b.created_at, '%Y-%m') ORDER BY period;";
                break;
            case "year":
                sql = "SELECT DATE_FORMAT(b.created_at, '%Y') AS period, COALESCE(SUM(b.total_amount),0) AS amt " +
                        "FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE_FORMAT(b.created_at, '%Y') ORDER BY period;";
                break;
            default:
                // day
                sql = "SELECT DATE(b.created_at) AS period, COALESCE(SUM(b.total_amount),0) AS amt " +
                        "FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE(b.created_at) ORDER BY period;";
                break;
        }

        // compute sensible defaults if from/to null
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        if (from == null || to == null) {
            if ("month".equalsIgnoreCase(period)) {
                to = LocalDate.now();
                from = to.minusMonths(11).withDayOfMonth(1);
            } else if ("year".equalsIgnoreCase(period)) {
                to = LocalDate.now();
                from = to.minusYears(4).withDayOfYear(1);
            } else {
                // day
                to = LocalDate.now();
                from = to.minusDays(29);
            }
        }

        Instant fromTs = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant toTs = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        Query q = em.createNativeQuery(sql);
        q.setParameter("fromTs", fromTs);
        q.setParameter("toTs", toTs);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<RevenueDto> out = new ArrayList<>();
        for (Object[] row : rows) {
            String periodLabel = Objects.toString(row[0], null);
            BigDecimal amt = row[1] == null ? BigDecimal.ZERO : new BigDecimal(row[1].toString());
            out.add(new RevenueDto(periodLabel, amt));
        }
        return out;
    }

    /**
     * Booking trends (counts) with the same period granularities as revenue.
     */
    @Transactional(readOnly = true)
    public List<BookingTrendDto> bookingTrends(String period, LocalDate from, LocalDate to) {
        if (period == null || period.isBlank()) period = "day";
        String sql;
        switch (period.toLowerCase()) {
            case "month":
                sql = "SELECT DATE_FORMAT(b.created_at, '%Y-%m') AS period, COUNT(*) AS cnt FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE_FORMAT(b.created_at, '%Y-%m') ORDER BY period;";
                break;
            case "year":
                sql = "SELECT DATE_FORMAT(b.created_at, '%Y') AS period, COUNT(*) AS cnt FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE_FORMAT(b.created_at, '%Y') ORDER BY period;";
                break;
            default:
                sql = "SELECT DATE(b.created_at) AS period, COUNT(*) AS cnt FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs GROUP BY DATE(b.created_at) ORDER BY period;";
                break;
        }

        if (from == null || to == null) {
            if ("month".equalsIgnoreCase(period)) {
                to = LocalDate.now();
                from = to.minusMonths(11).withDayOfMonth(1);
            } else if ("year".equalsIgnoreCase(period)) {
                to = LocalDate.now();
                from = to.minusYears(4).withDayOfYear(1);
            } else {
                to = LocalDate.now();
                from = to.minusDays(29);
            }
        }
        Instant fromTs = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant toTs = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        Query q = em.createNativeQuery(sql);
        q.setParameter("fromTs", fromTs);
        q.setParameter("toTs", toTs);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<BookingTrendDto> out = new ArrayList<>();
        for (Object[] row : rows) {
            String p = Objects.toString(row[0], null);
            Long cnt = row[1] == null ? 0L : ((Number) row[1]).longValue();
            out.add(new BookingTrendDto(p, cnt));
        }
        return out;
    }

    /**
     * Popular items across bookings. Limits default to 10.
     * Returns itemId, bookingType, bookings count — resolves name from respective repository when possible.
     */
    @Transactional(readOnly = true)
    public List<PopularItemDto> popularItems(int limit, LocalDate from, LocalDate to) {
        if (limit <= 0) limit = 10;

        if (from == null || to == null) {
            to = LocalDate.now();
            from = to.minusMonths(3);
        }
        Instant fromTs = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant toTs = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        String sql = "SELECT b.item_id AS itemId, b.booking_type AS type, COUNT(*) AS cnt " +
                "FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs " +
                "GROUP BY b.item_id, b.booking_type ORDER BY cnt DESC LIMIT :limit;";

        Query q = em.createNativeQuery(sql);
        q.setParameter("fromTs", fromTs);
        q.setParameter("toTs", toTs);
        q.setParameter("limit", limit);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<PopularItemDto> out = new ArrayList<>();
        for (Object[] r : rows) {
            byte[] bytes = (byte[]) r[0]; // UUID stored as binary(16)
            UUID itemId = toUuid(bytes);
            String bookingTypeRaw = Objects.toString(r[1], null);
            Long cnt = r[2] == null ? 0L : ((Number) r[2]).longValue();
            String itemType = (bookingTypeRaw == null) ? null : bookingTypeRaw.toUpperCase();
            String name = resolveItemName(itemId, itemType);
            out.add(PopularItemDto.builder().itemId(itemId).itemType(itemType).itemName(name).bookings(cnt).build());
        }
        return out;
    }

    /**
     * Customer analytics: counts grouped by country.
     * Assumes users table has "country" column on address or as direct column - adjust SQL if needed.
     */
    @Transactional(readOnly = true)
    public CustomerAnalyticsDto customerAnalytics() {
        // Try reading user country column - adjust column name depending on your schema.
        // This SQL assumes `users` table has column `country` (if Address is embedded as columns, it might be `address_country`)
        String sql = "SELECT COALESCE(u.country, 'UNKNOWN') AS country, COUNT(*) AS cnt FROM users u GROUP BY COALESCE(u.country, 'UNKNOWN') ORDER BY cnt DESC;";
        Query q = em.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] r : rows) {
            String country = Objects.toString(r[0], "UNKNOWN");
            Long cnt = r[1] == null ? 0L : ((Number) r[1]).longValue();
            map.put(country, cnt);
        }
        return CustomerAnalyticsDto.builder().customersByCountry(map).build();
    }

    /**
     * Seller performance: bookings count and revenue per seller (top N).
     */
    @Transactional(readOnly = true)
    public List<SellerPerformanceDto> sellerPerformance(int limit, LocalDate from, LocalDate to) {
        if (limit <= 0) limit = 10;
        if (from == null || to == null) {
            to = LocalDate.now();
            from = to.minusMonths(6);
        }
        Instant fromTs = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant toTs = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        String sql = "SELECT COALESCE(b.seller_id, x.seller_id) AS seller_id, COUNT(*) AS cnt, COALESCE(SUM(b.total_amount),0) AS revenue " +
                "FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs AND b.seller_id IS NOT NULL GROUP BY b.seller_id ORDER BY revenue DESC LIMIT :limit;";

        Query q = em.createNativeQuery(sql);
        q.setParameter("fromTs", fromTs);
        q.setParameter("toTs", toTs);
        q.setParameter("limit", limit);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        List<SellerPerformanceDto> out = new ArrayList<>();
        for (Object[] r : rows) {
            byte[] bytes = (byte[]) r[0];
            UUID sellerId = toUuid(bytes);
            Long cnt = r[1] == null ? 0L : ((Number) r[1]).longValue();
            BigDecimal revenue = r[2] == null ? BigDecimal.ZERO : new BigDecimal(r[2].toString());
            String sellerName = userRepository.findById(sellerId).map(u -> u.getName() == null ? u.getEmail() : u.getName()).orElse("Unknown");
            out.add(SellerPerformanceDto.builder().sellerId(sellerId).sellerName(sellerName).bookingsCount(cnt).revenue(revenue).build());
        }
        return out;
    }

    /**
     * Overall customer satisfaction: average of all review ratings across hotels and packages (extend with other review sources).
     * Uses hotels.reviews and package_reviews tables.
     */
    @Transactional(readOnly = true)
    public Double overallCustomerSatisfaction() {
        String sql = "SELECT AVG(r.rating) FROM (" +
                " SELECT hr.rating FROM hotel_reviews hr " +
                " UNION ALL " +
                " SELECT pr.rating FROM package_reviews pr " +
                ") r;";
        Query q = em.createNativeQuery(sql);
        Object res = q.getSingleResult();
        if (res == null) return 0.0;
        return ((Number) res).doubleValue();
    }

    /**
     * Dashboard aggregator.
     */
    @Transactional(readOnly = true)
    public DashboardDto dashboard(LocalDate from, LocalDate to) {
        // total revenue overall in date range
        if (from == null || to == null) {
            to = LocalDate.now();
            from = to.minusMonths(3);
        }
        Instant fromTs = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant toTs = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).minusNanos(1).toInstant();

        String revSql = "SELECT COALESCE(SUM(b.total_amount),0) FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs;";
        Query revQ = em.createNativeQuery(revSql);
        revQ.setParameter("fromTs", fromTs);
        revQ.setParameter("toTs", toTs);
        Object revObj = revQ.getSingleResult();
        BigDecimal totalRevenue = revObj == null ? BigDecimal.ZERO : new BigDecimal(revObj.toString());

        String bookingsSql = "SELECT COUNT(*) FROM bookings b WHERE b.created_at BETWEEN :fromTs AND :toTs;";
        Query bookingsQ = em.createNativeQuery(bookingsSql);
        bookingsQ.setParameter("fromTs", fromTs);
        bookingsQ.setParameter("toTs", toTs);
        Object bookingsObj = bookingsQ.getSingleResult();
        Long totalBookings = bookingsObj == null ? 0L : ((Number) bookingsObj).longValue();

        List<PopularItemDto> topItems = popularItems(5, from, to);
        List<SellerPerformanceDto> topSellers = sellerPerformance(5, from, to);
        CustomerAnalyticsDto customerAnalytics = customerAnalytics();
        Double satisfaction = overallCustomerSatisfaction();

        return DashboardDto.builder()
                .totalRevenue(totalRevenue)
                .totalBookings(totalBookings)
                .topItems(topItems)
                .topSellers(topSellers)
                .customerAnalytics(customerAnalytics)
                .overallCustomerSatisfaction(satisfaction)
                .build();
    }

    // ---------- helpers ----------

    private UUID toUuid(byte[] bytes) {
        if (bytes == null) return null;
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    private String resolveItemName(UUID itemId, String itemType) {
        if (itemId == null || itemType == null) return null;
        try {
            if (BookingType.HOTEL.name().equalsIgnoreCase(itemType)) {
                Optional<Hotel> h = hotelRepository.findById(itemId);
                return h.map(Hotel::getName).orElse(null);
            } else if (BookingType.PACKAGE.name().equalsIgnoreCase(itemType)) {
                Optional<TravelPackage> p = packageRepository.findById(itemId);
                return p.map(TravelPackage::getName).orElse(null);
            } else if ("EVENT".equalsIgnoreCase(itemType)) {
                Optional<Event> e = eventRepository.findById(itemId);
                return e.map(Event::getTitle).orElse(null);
            }
        } catch (Exception ex) {
            // ignore and return null
        }
        return null;
    }
}
