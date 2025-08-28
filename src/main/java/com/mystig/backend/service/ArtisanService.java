package com.mystig.backend.service;

import com.mystig.backend.dto.artisan.*;
import com.mystig.backend.mapper.ArtisanMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.artisan.ArtisanProduct;
import com.mystig.backend.repository.artisan.ArtisanRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.ArtisanSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArtisanService {

    private final ArtisanRepository artisanRepo;
    private final UserRepository users;

    @Transactional
    public ArtisanResponseDto get(UUID id) {
        ArtisanProduct p = artisanRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Artisan product not found"));
        return ArtisanMapper.toDto(p);
    }

    @Transactional
    public ArtisanListResponseDto search(String q,
                                         com.mystig.backend.model.enums.ArtisanCategory category,
                                         com.mystig.backend.model.enums.ArtisanStatus status,
                                         UUID ownerId,
                                         BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         String material,
                                         int page,
                                         int size,
                                         String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));
        Specification<ArtisanProduct> spec = Specification.where(ArtisanSpecifications.qLike(q))
                .and(ArtisanSpecifications.categoryEq(category))
                .and(ArtisanSpecifications.statusEq(status))
                .and(ArtisanSpecifications.ownerEq(ownerId))
                .and(ArtisanSpecifications.priceBetween(minPrice, maxPrice))
                .and(ArtisanSpecifications.hasMaterial(material));

        Page<ArtisanProduct> result = artisanRepo.findAll(spec, pageable);
        List<ArtisanResponseDto> items = result.getContent().stream().map(ArtisanMapper::toDto).toList();

        return ArtisanListResponseDto.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(items)
                .build();
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ArtisanResponseDto create(ArtisanRequestDto req) {
        ArtisanProduct p = ArtisanMapper.toEntity(req);
        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            p.setOwner(owner);
        }
        ArtisanProduct saved = artisanRepo.save(p);
        return ArtisanMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public ArtisanResponseDto update(UUID id, ArtisanRequestDto req) {
        ArtisanProduct p = artisanRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Artisan product not found"));

        // replace simple fields
        p.setName(req.getName());
        p.setCategory(req.getCategory());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setCurrency(req.getCurrency() == null ? p.getCurrency() : req.getCurrency());
        p.setOrigin(req.getOrigin());
        p.setCraftsman(req.getCraftsman());
        p.setMaterials(req.getMaterials() == null ? p.getMaterials() : req.getMaterials());
        p.setDimensions(req.getDimensions());
        p.setInStock(req.getInStock() == null ? p.isInStock() : req.getInStock());
        p.setQuantity(req.getQuantity());
        p.setStatus(req.getStatus());

        // replace images
        p.getImages().clear();
        if (req.getImages() != null) {
            List<com.mystig.backend.model.artisan.ArtisanImage> imgs = req.getImages().stream().map(i ->
                    com.mystig.backend.model.artisan.ArtisanImage.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).artisan(p).build()
            ).toList();
            p.getImages().addAll(imgs);
        }

        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            p.setOwner(owner);
        }

        ArtisanProduct saved = artisanRepo.save(p);
        return ArtisanMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        artisanRepo.deleteById(id);
    }

    private Sort toSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        List<Sort.Order> orders = Arrays.stream(sort.split(";"))
                .map(s -> s.split(","))
                .map(a -> a.length == 2
                        ? new Sort.Order("asc".equalsIgnoreCase(a[1]) ? Sort.Direction.ASC : Sort.Direction.DESC, a[0])
                        : new Sort.Order(Sort.Direction.ASC, a[0]))
                .toList();
        return Sort.by(orders);
    }
}

