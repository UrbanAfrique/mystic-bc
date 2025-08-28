package com.mystig.backend.repository.food;

import com.mystig.backend.model.food.FoodExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<FoodExperience, UUID>, JpaSpecificationExecutor<FoodExperience> {}

