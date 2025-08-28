package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.Transports;
import com.mystig.backend.repository.travel.TravelTransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TravelTransportService {
    private final TravelTransportRepository transportRepo;

    public List<Transports> findAll() {
        return transportRepo.findAll();
    }

    public List<Transports> findByCity(UUID cityId) {
        return transportRepo.findByCityId(cityId);
    }
    public Transports findById(UUID id) {
        return transportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport not found with id: " + id));
    }
    public Transports save(Transports transport) {
        return transportRepo.save(transport);
    }

    public void delete(UUID id) {
        transportRepo.deleteById(id);
    }
}
