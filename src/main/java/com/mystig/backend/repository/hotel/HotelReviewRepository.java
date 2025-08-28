package com.mystig.backend.repository.hotel;

import com.mystig.backend.model.hotel.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelReviewRepository extends JpaRepository<HotelReview, UUID> {}

