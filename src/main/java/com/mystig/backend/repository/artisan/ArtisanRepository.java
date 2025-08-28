package com.mystig.backend.repository.artisan;

import com.mystig.backend.model.artisan.ArtisanProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtisanRepository extends JpaRepository<ArtisanProduct, UUID>, JpaSpecificationExecutor<ArtisanProduct> {}

