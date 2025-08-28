package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.DemandCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DemandCityRepository extends JpaRepository<DemandCity, UUID> {
    List<DemandCity> findByDemandId(UUID demandId);
}
