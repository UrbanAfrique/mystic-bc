package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, UUID> {
    List<ServiceOffering> findByCityId(UUID cityId);
}
