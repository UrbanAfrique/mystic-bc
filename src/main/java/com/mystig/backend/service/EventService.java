package com.mystig.backend.service;

import com.mystig.backend.dto.event.*;
import com.mystig.backend.mapper.EventMapper;
import com.mystig.backend.model.User;
import com.mystig.backend.model.embedded.TicketPricing;
import com.mystig.backend.model.event.*;
import com.mystig.backend.model.enums.EventStatus;
import com.mystig.backend.repository.event.*;
import com.mystig.backend.repository.UserRepository;
import com.mystig.backend.spec.EventSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.Arrays;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository events;
    private final TicketRepository tickets;
    private final AttendeeRepository attendees;
    private final UserRepository users;

//    public EventListResponseDto search(
//            String q,
//            String city,
//            LocalDate from,
//            LocalDate to,
//            EventStatus status,
//            List<String> tags,
//            int page,
//            int size,
//            String sort
//    ) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
//        Page<Event> result = events.findAll(
//            EventSpecifications.search(q, city, from, to, status, tags),
//            pageable
//        );
//        List<EventResponseDto> items = result.getContent().stream()
//            .map(EventMapper::toDto)
//            .toList();
//        return EventListResponseDto.builder()
//            .items(items)
//            .totalElements(result.getTotalElements())
//            .totalPages(result.getTotalPages())
//            .page(result.getNumber())
//            .size(result.getSize())
//            .build();
//    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public EventResponseDto create(EventRequestDto req) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // usually the username/email used to login
        User organizer = users.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        Event e = EventMapper.toEntity(req, organizer);
        Event saved = events.save(e);
        return EventMapper.toDto(saved);
    }
    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public EventResponseDto update(UUID id, EventRequestDto req) {
        Event existing = events.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User organizer = users.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Authenticated user not found"));

        // Map request to entity
        Event updated = EventMapper.toEntity(req, organizer);
        updated.setId(existing.getId());

        // Clear old children
        existing.getImages().clear();
        existing.getTickets().clear();

        // Update simple fields
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setType(updated.getType());
        existing.setCategory(updated.getCategory());
        existing.setDateRange(updated.getDateRange());
        existing.setTimeRange(updated.getTimeRange());
        existing.setLocation(updated.getLocation());
        existing.setOrganizer(updated.getOrganizer());
        existing.setStatus(updated.getStatus());
        existing.setFeatured(updated.isFeatured());
        existing.setTags(updated.getTags());
        existing.setSocialMedia(updated.getSocialMedia());
        existing.setRequirements(updated.getRequirements());

        if (updated.getImages() != null) existing.getImages().addAll(updated.getImages());
        if (updated.getTickets() != null) existing.getTickets().addAll(updated.getTickets());

        Event saved = events.save(existing);
        return EventMapper.toDto(saved);
    }

    @Transactional
   // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SELLER')")
    public void delete(UUID id) {
        events.deleteById(id);
    }

    @Transactional
    public EventResponseDto get(UUID id) {
        return events.findById(id).map(EventMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    @Transactional
    public Page<EventResponseDto> search(String q, String city, LocalDate from, LocalDate to, EventStatus status, List<String> tags, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, toSort(sort));

        Specification<Event> spec = Specification.where(EventSpecifications.titleOrDescriptionLike(q))
                .and(EventSpecifications.cityEquals(city))
                .and(EventSpecifications.dateBetween(from, to))
                .and(EventSpecifications.statusEq(status))
                .and(EventSpecifications.tagsIn(tags == null ? null : Set.copyOf(tags)));

        Page<Event> res = events.findAll(spec, pageable);
        return res.map(EventMapper::toDto);
    }

    private Sort toSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        List<Sort.Order> orders = Arrays.stream(sort.split(";"))
                .map(s -> s.split(","))
                .map(a -> a.length == 2 ? new Sort.Order("asc".equalsIgnoreCase(a[1]) ? Sort.Direction.ASC : Sort.Direction.DESC, a[0])
                        : new Sort.Order(Sort.Direction.ASC, a[0])).toList();
        return Sort.by(orders);
    }

    @Transactional
    public AttendeeResponseDto purchaseTicket(UUID eventId, PurchaseTicketRequest req, User buyerOrNull) {
        Event e = events.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        Ticket t = tickets.findById(req.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket type not found"));

        int remaining = t.getQuantity() - t.getSold();
        if (req.getQuantity() == null || req.getQuantity() <= 0) throw new IllegalArgumentException("Quantity must be > 0");
        if (req.getQuantity() > remaining) throw new IllegalArgumentException("Not enough tickets available");

        // decrease availability (update sold)
        t.setSold(t.getSold() + req.getQuantity());
        tickets.save(t);

        // create attendees (one record per ticket)
        Attendee last = null;
        for (int i = 0; i < req.getQuantity(); i++) {
            Attendee a = Attendee.builder()
                    .event(e)
                    .ticket(t)
                    .user(buyerOrNull)
                    .name(req.getName())
                    .email(req.getEmail())
                    .phone(req.getPhone())
                    .build();
            last = attendees.save(a);
        }

        return EventMapper.attendeeToDto(last);
    }

    @Transactional
    public List<AttendeeResponseDto> attendeesForEvent(UUID eventId) {
        List<Attendee> list = attendees.findByEventId(eventId);
        return list.stream().map(EventMapper::attendeeToDto).toList();
    }
}

