package com.mystig.backend.service;

import com.mystig.backend.dto.food.*;
import com.mystig.backend.mapper.FoodMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.food.FoodImage;
import com.mystig.backend.model.food.FoodExperience;
import com.mystig.backend.repository.food.FoodRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.FoodSpecifications;
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
public class FoodService {

    private final FoodRepository foods;
    private final UserRepository users;

    @Transactional
    public FoodResponseDto get(UUID id) {
        FoodExperience f = foods.findById(id).orElseThrow(() -> new EntityNotFoundException("Food experience not found"));
        return FoodMapper.toDto(f);
    }

    @Transactional
    public FoodListResponseDto search(String q,
                                      com.mystig.backend.model.enums.FoodType type,
                                      com.mystig.backend.model.enums.DifficultyLevel difficulty,
                                      com.mystig.backend.model.enums.FoodStatus status,
                                      UUID ownerId,
                                      BigDecimal minPrice,
                                      BigDecimal maxPrice,
                                      Integer participants,
                                      int page,
                                      int size,
                                      String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<FoodExperience> spec = Specification.where(FoodSpecifications.qLike(q))
                .and(FoodSpecifications.typeEq(type))
                .and(FoodSpecifications.difficultyEq(difficulty))
                .and(FoodSpecifications.statusEq(status))
                .and(FoodSpecifications.ownerEq(ownerId))
                .and(FoodSpecifications.priceBetween(minPrice, maxPrice))
                .and(FoodSpecifications.fitsParticipants(participants));

        Page<FoodExperience> result = foods.findAll(spec, pageable);
        List<FoodResponseDto> items = result.getContent().stream().map(FoodMapper::toDto).toList();

        return FoodListResponseDto.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(items)
                .build();
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public FoodResponseDto create(FoodRequestDto req) {
        FoodExperience f = FoodMapper.toEntity(req);
        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            f.setOwner(owner);
        }
        FoodExperience saved = foods.save(f);
        return FoodMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public FoodResponseDto update(UUID id, FoodRequestDto req) {
        FoodExperience f = foods.findById(id).orElseThrow(() -> new EntityNotFoundException("Food experience not found"));

        f.setName(req.getName());
        f.setType(req.getType());
        f.setDescription(req.getDescription());
        f.setDuration(req.getDuration());
        f.setPrice(req.getPrice());
        f.setCurrency(req.getCurrency() == null ? f.getCurrency() : req.getCurrency());
        f.setLocation(req.getLocation());
        f.setIncludes(req.getIncludes() == null ? f.getIncludes() : req.getIncludes());
        f.setMaxParticipants(req.getMaxParticipants());
        f.setDifficulty(req.getDifficulty());
        f.setStatus(req.getStatus());

        // images replace
        f.getImages().clear();
        if (req.getImages() != null) {
            List<FoodImage> imgs = req.getImages().stream().map(i ->
                    FoodImage.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).food(f).build()
            ).toList();
            f.getImages().addAll(imgs);
        }

        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            f.setOwner(owner);
        }

        FoodExperience saved = foods.save(f);
        return FoodMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        foods.deleteById(id);
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

