package com.mystig.backend.service;

import com.mystig.backend.dto.event.*;
import com.mystig.backend.model.event.Ticket;
import com.mystig.backend.model.event.Event;
import com.mystig.backend.repository.event.EventTicketRepository;
import com.mystig.backend.repository.event.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final EventTicketRepository ticketRepo;
    private final EventRepository eventRepo;

    public List<TicketResponseDto> list(UUID eventId) {
        List<Ticket> tickets = eventId != null ? ticketRepo.findByEventId(eventId) : ticketRepo.findAll();
        return tickets.stream().map(this::toDto).collect(Collectors.toList());
    }

    public TicketResponseDto get(UUID id) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return toDto(ticket);
    }

    public TicketResponseDto create(TicketRequestDto req) {
        Event event = eventRepo.findById(req.getEventId()).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        Ticket ticket = Ticket.builder()
                .typeName(req.getTypeName())
                .price(req.getPrice())
                .currency(req.getCurrency())
                .quantity(req.getQuantity())
                .sold(0)
                .event(event)
                .build();
        ticket = ticketRepo.save(ticket);
        return toDto(ticket);
    }

    public TicketResponseDto update(UUID id, TicketRequestDto req) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        ticket.setTypeName(req.getTypeName());
        ticket.setPrice(req.getPrice());
        ticket.setCurrency(req.getCurrency());
        ticket.setQuantity(req.getQuantity());
        ticketRepo.save(ticket);
        return toDto(ticket);
    }

    public void delete(UUID id) {
        ticketRepo.deleteById(id);
    }

    private TicketResponseDto toDto(Ticket t) {
        return TicketResponseDto.builder()
                .id(t.getId())
                .typeName(t.getTypeName())
                .price(t.getPrice())
                .currency(t.getCurrency())
                .quantity(t.getQuantity())
                .sold(t.getSold())
                .eventId(t.getEvent().getId())
                .eventTitle(t.getEvent().getTitle())
                .build();
    }
}