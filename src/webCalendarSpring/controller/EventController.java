package webCalendarSpring.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.repository.RepoInterface;
import webCalendarSpring.dto.EventRequestDTO;
import webCalendarSpring.dto.EventResponseDTO;
import webCalendarSpring.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private RepoInterface repoInterface;

    @GetMapping("/event/today")
    public ResponseEntity<List<EventResponseDTO>> getTodayEvents() {
        LocalDate today = LocalDate.now();
        List<Event> todayEvents = repoInterface.findByDate(today);
        List<EventResponseDTO> dtos = todayEvents.stream()
                .map(event -> new EventResponseDTO(event.getId(), event.getEvent(), event.getDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(
            @RequestParam(required = false) String start_time,
            @RequestParam(required = false) String end_time) {

        List<Event> events;
        if (start_time != null && end_time != null) {
            LocalDate start = LocalDate.parse(start_time);
            LocalDate end = LocalDate.parse(end_time);
            events = repoInterface.findByDateBetween(start, end);
        } else {
            events = repoInterface.findAll();
        }

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EventResponseDTO> dtos = events.stream()
                .map(event -> new EventResponseDTO(event.getId(), event.getEvent(), event.getDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        Optional<Event> event = repoInterface.findById(id);
        if (!event.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "The event doesn't exist!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }

        Event eventObj = event.get();
        EventResponseDTO responseDTO = new EventResponseDTO(eventObj.getId(), eventObj.getEvent(), eventObj.getDate());
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        Optional<Event> eventOptional = repoInterface.findById(id);
        if (!eventOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "The event doesn't exist!"));
        }

        Event event = eventOptional.get();
        EventResponseDTO responseDTO = new EventResponseDTO(event.getId(), event.getEvent(), event.getDate());
        repoInterface.deleteById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/event")
    public ResponseEntity<EventResponseDTO> postFunction(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        Event event = new Event();
        event.setEvent(eventRequestDTO.getEvent());
        event.setDate(eventRequestDTO.getDate());
        Event savedEvent = repoInterface.save(event);
        EventResponseDTO responseDTO = new EventResponseDTO("The event has been added!", savedEvent.getEvent(), savedEvent.getDate());
        return ResponseEntity.ok(responseDTO);
    }

}
