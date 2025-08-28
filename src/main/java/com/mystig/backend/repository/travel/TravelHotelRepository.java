package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TravelHotelRepository extends JpaRepository<Hotel, UUID> {
    List<Hotel> findByCityId(UUID cityId);
}
