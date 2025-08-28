package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.*;
import com.mystig.backend.repository.travel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DemandService {
    private final DemandRepository demandRepo;
    private final DemandCityRepository demandCityRepo;

    private final ActivityRepository activityRepo;
    private final ServiceOfferingRepository serviceRepo;
    private final TravelHotelRepository hotelRepo;
    private final TravelTransportRepository transportRepo;

    // ... existing methods ...

    public DemandCity updateServices(UUID demandCityId, List<UUID> serviceIds) {
        DemandCity demandCity = demandCityRepo.findById(demandCityId)
                .orElseThrow(() -> new RuntimeException("DemandCity not found"));

        List<ServiceOffering> services = serviceRepo.findAllById(serviceIds);
        demandCity.setServices(services);

        return demandCityRepo.save(demandCity);
    }

    public DemandCity updateActivities(UUID demandCityId, List<UUID> activityIds) {
        DemandCity demandCity = demandCityRepo.findById(demandCityId)
                .orElseThrow(() -> new RuntimeException("DemandCity not found"));

        List<Activity> activities = activityRepo.findAllById(activityIds);
        demandCity.setActivities(activities);

        return demandCityRepo.save(demandCity);
    }

    public DemandCity updateHotel(UUID demandCityId, UUID hotelId) {
        DemandCity demandCity = demandCityRepo.findById(demandCityId)
                .orElseThrow(() -> new RuntimeException("DemandCity not found"));

        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        demandCity.setSelectedHotel(hotel);

        return demandCityRepo.save(demandCity);
    }

    public DemandCity updateTransport(UUID demandCityId, UUID transportId) {
        DemandCity demandCity = demandCityRepo.findById(demandCityId)
                .orElseThrow(() -> new RuntimeException("DemandCity not found"));

        Transports transport = transportRepo.findById(transportId)
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        demandCity.setSelectedTransport(transport);

        return demandCityRepo.save(demandCity);
    }

    // Helper method to get all demand cities for a specific demand
    public List<DemandCity> getDemandCitiesByDemandId(UUID demandId) {
        return demandCityRepo.findByDemandId(demandId);
    }

    public List<Demand> findAll() {
        return demandRepo.findAll();
    }

    public Demand findById(UUID id) {
        return demandRepo.findById(id).orElseThrow();
    }

    public Demand save(Demand demand) {
        if (demand.getCities() != null) {
            for (DemandCity city : demand.getCities()) {
                city.setDemand(demand);
            }
        }
        return demandRepo.save(demand);
    }

    public void delete(UUID id) {
        demandRepo.deleteById(id);
    }

    public BigDecimal calculateTotal(UUID demandId) {
        List<DemandCity> demandCities = demandCityRepo.findByDemandId(demandId);

        return demandCities.stream().map(dc -> {
            // Hotel cost - convert Double to BigDecimal first
            BigDecimal hotelCost = (dc.getSelectedHotel() != null)
                    ? BigDecimal.valueOf(dc.getSelectedHotel().getPrice()) // Convert Double to BigDecimal
                    .multiply(BigDecimal.valueOf(dc.getDurationDays()))
                    : BigDecimal.ZERO;

            // Transport cost - convert Double to BigDecimal first
            BigDecimal transportCost = (dc.getSelectedTransport() != null)
                    ? BigDecimal.valueOf(dc.getSelectedTransport().getPrice()) // Convert Double to BigDecimal
                    .multiply(BigDecimal.valueOf(dc.getDurationDays()))
                    : BigDecimal.ZERO;

            // Activities cost - convert each Double to BigDecimal
            BigDecimal activitiesCost = dc.getActivities().stream()
                    .map(activity -> BigDecimal.valueOf(activity.getPrice())) // Convert Double to BigDecimal
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Services cost - convert each Double to BigDecimal
            BigDecimal servicesCost = dc.getServices().stream()
                    .map(service -> BigDecimal.valueOf(service.getPrice())) // Convert Double to BigDecimal
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return hotelCost.add(transportCost).add(activitiesCost).add(servicesCost);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}