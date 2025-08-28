package com.mystig.backend.repository.packagepkg;

import com.mystig.backend.model.packagepkg.PackageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PackageReviewRepository extends JpaRepository<PackageReview, UUID> {}

