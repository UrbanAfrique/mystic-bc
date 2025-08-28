package com.mystig.backend.service;

import com.mystig.backend.dto.transport.*;
import com.mystig.backend.mapper.TransportMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.transport.Transport;
import com.mystig.backend.repository.transport.TransportRepository;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.TransportSpecifications;
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
public class TransportService {

    private final TransportRepository transports;
    private final UserRepository users;

    @Transactional
    public TransportResponseDto get(UUID id) {
        Transport t = transports.findById(id).orElseThrow(() -> new EntityNotFoundException("Transport not found"));
        return TransportMapper.toDto(t);
    }

    @Transactional
    public TransportListResponseDto search(String q,
                                           com.mystig.backend.model.enums.TransportType type,
                                           com.mystig.backend.model.enums.TransportStatus status,
                                           java.util.UUID ownerId,
                                           BigDecimal minPrice,
                                           BigDecimal maxPrice,
                                           String city,
                                           String feature,
                                           int page,
                                           int size,
                                           String sort) {

        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<Transport> spec = Specification.where(TransportSpecifications.qLike(q))
                .and(TransportSpecifications.typeEq(type))
                .and(TransportSpecifications.statusEq(status))
                .and(TransportSpecifications.ownerEq(ownerId))
                .and(TransportSpecifications.priceBetween(minPrice, maxPrice))
                .and(TransportSpecifications.availableInCity(city))
                .and(TransportSpecifications.hasFeature(feature));

        Page<Transport> result = transports.findAll(spec, pageable);
        List<TransportResponseDto> items = result.getContent().stream().map(TransportMapper::toDto).toList();

        return TransportListResponseDto.builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .page(result.getNumber())
                .size(result.getSize())
                .items(items)
                .build();
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public TransportResponseDto create(TransportRequestDto req) {
        Transport t = TransportMapper.toEntity(req);
        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            t.setOwner(owner);
        }
        Transport saved = transports.save(t);
        return TransportMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public TransportResponseDto update(UUID id, TransportRequestDto req) {
        Transport t = transports.findById(id).orElseThrow(() -> new EntityNotFoundException("Transport not found"));
        // copy updatable fields
        t.setType(req.getType());
        t.setName(req.getName());
        t.setDescription(req.getDescription());
        t.setPrice(req.getPrice());
        t.setCurrency(req.getCurrency() == null ? t.getCurrency() : req.getCurrency());
        t.setCapacity(req.getCapacity());
        t.setFeatures(req.getFeatures() == null ? t.getFeatures() : req.getFeatures());
        t.setAvailability(TransportMapper.toEntity(req).getAvailability());
        t.setStatus(req.getStatus());

        if (req.getOwnerId() != null) {
            User owner = users.findById(req.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Owner not found"));
            t.setOwner(owner);
        }
        Transport saved = transports.save(t);
        return TransportMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        transports.deleteById(id);
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

