package com.mystig.backend.mapper;

import com.mystig.backend.dto.artisan.*;
import com.mystig.backend.model.artisan.*;
import lombok.experimental.UtilityClass;
import java.util.Set;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ArtisanMapper {

    public ArtisanProduct toEntity(ArtisanRequestDto r) {
        ArtisanProduct p = new ArtisanProduct();
        p.setName(r.getName());
        p.setCategory(r.getCategory());
        p.setDescription(r.getDescription());
        p.setPrice(r.getPrice());
        p.setCurrency(r.getCurrency() == null ? "MAD" : r.getCurrency());
        p.setOrigin(r.getOrigin());
        p.setCraftsman(r.getCraftsman());
        p.setMaterials(r.getMaterials() == null ? Set.of() : r.getMaterials());
        p.setDimensions(r.getDimensions());
        p.setInStock(r.getInStock() == null ? Boolean.TRUE : r.getInStock());
        p.setQuantity(r.getQuantity());
        p.setStatus(r.getStatus());
        if (r.getImages() != null) {
            List<ArtisanImage> imgs = r.getImages().stream().map(i ->
                    ArtisanImage.builder().url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).artisan(p).build()
            ).toList();
            p.getImages().addAll(imgs);
        }
        return p;
    }

    public ArtisanResponseDto toDto(ArtisanProduct p) {
        return ArtisanResponseDto.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory())
                .description(p.getDescription())
                .price(p.getPrice())
                .currency(p.getCurrency())
                .origin(p.getOrigin())
                .craftsman(p.getCraftsman())
                .materials(p.getMaterials())
                .dimensions(p.getDimensions())
                .images(p.getImages() == null ? List.of() : p.getImages().stream().map(i -> ArtisanImageDto.builder()
                        .url(i.getUrl()).caption(i.getCaption()).isPrimary(i.isPrimary()).build()).toList())
                .inStock(p.isInStock())
                .quantity(p.getQuantity())
                .ownerId(p.getOwner() == null ? null : p.getOwner().getId())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}

