package com.mystig.backend.controller;

import com.mystig.backend.dto.event.*;
import com.mystig.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService service;

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> list(@RequestParam(required = false) UUID eventId) {
        return ResponseEntity.ok(service.list(eventId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDto> create(@RequestBody TicketRequestDto req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDto> update(@PathVariable UUID id, @RequestBody TicketRequestDto req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}