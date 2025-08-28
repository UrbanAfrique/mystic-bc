package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.Hotel;
import com.mystig.backend.repository.travel.TravelHotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelHotelService  {
    private final TravelHotelRepository hotelRepo;

    public List<Hotel> findAll() {
        return hotelRepo.findAll();
    }

    public List<Hotel> findByCity(UUID cityId) {
        return hotelRepo.findByCityId(cityId);
    }
    public Hotel findById(UUID id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }

    public Hotel save(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public void delete(UUID id) {
        hotelRepo.deleteById(id);
    }
}
