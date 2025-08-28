package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.ServiceOffering;
import com.mystig.backend.repository.travel.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOfferingService {
    private final ServiceOfferingRepository serviceRepo;

    public List<ServiceOffering> findAll() {
        return serviceRepo.findAll();
    }
    public ServiceOffering findById(UUID id) {
        return serviceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service offering not found with id: " + id));
    }
    public List<ServiceOffering> findByCity(UUID cityId) {
        return serviceRepo.findByCityId(cityId);
    }

    public ServiceOffering save(ServiceOffering s) {
        return serviceRepo.save(s);
    }

    public void delete(UUID id) {
        serviceRepo.deleteById(id);
    }
}
