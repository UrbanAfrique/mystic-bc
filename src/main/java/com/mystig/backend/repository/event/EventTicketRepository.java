package com.mystig.backend.repository.event;

import com.mystig.backend.model.event.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventTicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByEventId(UUID eventId);
}