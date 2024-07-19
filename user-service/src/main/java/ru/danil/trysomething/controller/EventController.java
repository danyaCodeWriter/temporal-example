package ru.danil.trysomething.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import ru.danil.trysomething.entity.Event;
import ru.danil.trysomething.statuses.EventUserStatus;

@Tag(name = "Event main controller")
public interface EventController {

    ResponseEntity<EventUserStatus> createEvent(long userId);

    Event getRandomEvent();
}
