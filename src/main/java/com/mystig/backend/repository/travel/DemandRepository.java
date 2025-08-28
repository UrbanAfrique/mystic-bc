package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DemandRepository extends JpaRepository<Demand, UUID> {
}
