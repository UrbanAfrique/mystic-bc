package com.mystig.backend.service.travel;

import com.mystig.backend.model.travel.Activity;
import com.mystig.backend.repository.travel.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepo;

    public List<Activity> findAll() {
        return activityRepo.findAll();
    }
    public Activity findById(UUID id) {
        return activityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service offering not found with id: " + id));
    }
    public List<Activity> findByCity(UUID cityId) {
        return activityRepo.findByCityId(cityId);
    }

    public Activity save(Activity activity) {
        return activityRepo.save(activity);
    }

    public void delete(UUID id) {
        activityRepo.deleteById(id);
    }
}
