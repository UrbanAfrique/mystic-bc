package com.mystig.backend.repository.packagepkg;

import com.mystig.backend.model.packagepkg.TravelPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, UUID>, JpaSpecificationExecutor<TravelPackage> {}

