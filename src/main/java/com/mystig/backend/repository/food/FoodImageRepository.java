package com.mystig.backend.repository.food;

import com.mystig.backend.model.food.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, UUID> {}

