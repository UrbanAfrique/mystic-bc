package com.mystig.backend.repository.travel;

import com.mystig.backend.model.travel.SpecialPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecialPackageRepository extends JpaRepository<SpecialPackage, UUID> {
}
