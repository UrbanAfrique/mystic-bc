package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.City;
import com.mystig.backend.repository.travel.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepo;

    public List<City> findAll() {
        return cityRepo.findAll();
    }

    public City findById(UUID id) {
        return cityRepo.findById(id).orElseThrow();
    }

    public City save(City city) {
        return cityRepo.save(city);
    }

    public void delete(UUID id) {
        cityRepo.deleteById(id);
    }
}
