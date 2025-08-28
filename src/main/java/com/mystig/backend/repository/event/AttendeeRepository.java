package com.mystig.backend.repository.event;

import com.mystig.backend.model.event.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
    List<Attendee> findByEventId(java.util.UUID eventId);
}

