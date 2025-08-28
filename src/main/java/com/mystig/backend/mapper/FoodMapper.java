package com.mystig.backend.mapper;

import com.mystig.backend.dto.food.*;
import com.mystig.backend.model.food.*;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class FoodMapper {

    public FoodExperience toEntity(FoodRequestDto r) {
        FoodExperience f = new FoodExperience();
        f.setName(r.getName());
        f.setType(r.getType());
        f.setDescription(r.getDescription());
        f.setDuration(r.getDuration());
        f.setPrice(r.getPrice());
        f.setCurrency(r.getCurrency() == null ? "MAD" : r.getCurrency());
        f.setLocation(r.getLocation());
        f.setIncludes(r.getIncludes() == null ? List.of() : r.getIncludes());
        f.setMaxParticipants(r.getMaxParticipants());
        f.setDifficulty(r.getDifficulty());
        f.setStatus(r.getStatus());
        if (r.getImages() != null) {
            List<FoodImage> imgs = r.getImages().stream().map(i ->
                    FoodImage.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).food(f).build()
            ).toList();
            f.getImages().addAll(imgs);
        }
        return f;
    }

    public FoodResponseDto toDto(FoodExperience f) {
        return FoodResponseDto.builder()
                .id(f.getId())
                .name(f.getName())
                .type(f.getType())
                .description(f.getDescription())
                .duration(f.getDuration())
                .price(f.getPrice())
                .currency(f.getCurrency())
                .location(f.getLocation())
                .includes(f.getIncludes())
                .maxParticipants(f.getMaxParticipants())
                .difficulty(f.getDifficulty())
                .images(f.getImages() == null ? List.of() : f.getImages().stream().map(i -> FoodImageDto.builder()
                        .url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).build()).toList())
                .ownerId(f.getOwner() == null ? null : f.getOwner().getId())
                .status(f.getStatus())
                .createdAt(f.getCreatedAt())
                .updatedAt(f.getUpdatedAt())
                .build();
    }
}

