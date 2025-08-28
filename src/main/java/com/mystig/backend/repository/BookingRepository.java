package com.mystig.backend.repository;

import com.mystig.backend.model.booking.Booking;
import com.mystig.backend.model.enums.BookingStatus;
import com.mystig.backend.model.enums.BookingType;
import com.mystig.backend.model.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByBookingNumber(String bookingNumber);

    Page<Booking> findByBookingTypeAndItemId(BookingType type, UUID itemId, Pageable pageable);

    @Query("select count(b) from Booking b where b.bookingType = :type and b.itemId = :itemId and b.createdAt between :from and :to")
    long countForItemBetween(BookingType type, UUID itemId, Instant from, Instant to);
}


