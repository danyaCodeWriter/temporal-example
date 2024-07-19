package ru.danil.trysomething.activity;


import io.temporal.activity.ActivityInterface;
import ru.danil.trysomething.entity.Event;

import java.util.List;

@ActivityInterface
public interface RepositoryActivity {
    void saveEvent(Event event);

    List<Event> getEventsForProcessing();
}
