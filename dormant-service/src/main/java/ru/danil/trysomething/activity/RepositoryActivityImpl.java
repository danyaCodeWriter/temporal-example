package ru.danil.trysomething.activity;

import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.danil.trysomething.repository.EventRepository;
import ru.danil.trysomething.entity.Event;

import java.time.LocalDateTime;
import java.util.List;


@Component
@ActivityImpl(taskQueues = "DormantTaskQueue")
@RequiredArgsConstructor
public class RepositoryActivityImpl implements RepositoryActivity {

    private final EventRepository repository;

    @Override
    @Transactional
    public void saveEvent(Event event) {
        repository.save(event);
    }

    @Override
    @Transactional
    public List<Event> getEventsForProcessing() {
        return  repository.findAllByUpdatedDateAfter(LocalDateTime.now().minusMinutes(2));
    }
}
