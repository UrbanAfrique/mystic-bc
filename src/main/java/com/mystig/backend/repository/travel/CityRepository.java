package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}
