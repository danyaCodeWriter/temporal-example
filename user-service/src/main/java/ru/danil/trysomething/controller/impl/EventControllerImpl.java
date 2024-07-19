package ru.danil.trysomething.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danil.trysomething.controller.EventController;
import ru.danil.trysomething.entity.Event;
import ru.danil.trysomething.repository.EventRepository;
import ru.danil.trysomething.statuses.EventUserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventControllerImpl implements EventController {

    private final EventRepository eventRepository;

    @Override
    @PostMapping("/create")
    public ResponseEntity<EventUserStatus> createEvent(@RequestBody long userId) {
        Event eventEt = new Event();
        eventEt.setUserId(userId);
        EventUserStatus inProgress = EventUserStatus.NEW;
        eventEt.setStatus(inProgress);
        eventRepository.save(eventEt);
        return ResponseEntity.ok(inProgress);
    }

    @Override
    @GetMapping("/random")
    public Event getRandomEvent() {
        List<Event> eventsByCreateDateBetween = eventRepository
                .findEventsByCreateDateBetween(LocalDateTime
                        .now().minusDays(1), LocalDateTime.now());
        Random rand = new Random();
        int i = rand.nextInt(0, eventsByCreateDateBetween.size());
        return eventsByCreateDateBetween.get(i);
    }
}
