package ru.danil.trysomething.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danil.trysomething.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findEventsByCreateDateBetween(LocalDateTime start, LocalDateTime end);
}
