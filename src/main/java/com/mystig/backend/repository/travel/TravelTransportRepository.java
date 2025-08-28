package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.Transports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TravelTransportRepository extends JpaRepository<Transports, UUID> {
    List<Transports> findByCityId(UUID cityId);
}
