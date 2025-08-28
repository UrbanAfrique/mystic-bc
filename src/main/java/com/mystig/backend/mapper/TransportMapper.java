package com.mystig.backend.mapper;

import com.mystig.backend.dto.transport.*;
import com.mystig.backend.model.embedded.TransportAvailability;
import com.mystig.backend.model.transport.Transport;
import com.mystig.backend.model.transport.Transport;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

@UtilityClass
public class TransportMapper {

    public Transport toEntity(TransportRequestDto r) {
        Transport t = new Transport();
        t.setType(r.getType());
        t.setName(r.getName());
        t.setDescription(r.getDescription());
        t.setPrice(r.getPrice());
        t.setCurrency(r.getCurrency() == null ? "MAD" : r.getCurrency());
        t.setCapacity(r.getCapacity());
        t.setFeatures(r.getFeatures() == null ? Set.of() : r.getFeatures());
        t.setAvailability(TransportAvailability.builder()
                .cities(r.getAvailabilityCities() == null ? List.of() : List.copyOf(r.getAvailabilityCities()))
                .routes(r.getAvailabilityRoutes() == null ? List.of() : List.copyOf(r.getAvailabilityRoutes()))
                .build());
        t.setStatus(r.getStatus());
        return t;
    }

    public TransportResponseDto toDto(Transport t) {
        return TransportResponseDto.builder()
                .id(t.getId())
                .type(t.getType())
                .name(t.getName())
                .description(t.getDescription())
                .price(t.getPrice())
                .currency(t.getCurrency())
                .capacity(t.getCapacity())
                .features(t.getFeatures())
                .availabilityCities(t.getAvailability() == null ? List.of() : t.getAvailability().getCities())
                .availabilityRoutes(t.getAvailability() == null ? List.of() : t.getAvailability().getRoutes())
                .ownerId(t.getOwner() == null ? null : t.getOwner().getId())
                .status(t.getStatus())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}

