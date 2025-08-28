package com.mystig.backend.repository.artisan;

import com.mystig.backend.model.artisan.ArtisanImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtisanImageRepository extends JpaRepository<ArtisanImage, UUID> {}
