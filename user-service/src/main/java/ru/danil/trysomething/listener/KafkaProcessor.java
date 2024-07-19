package ru.danil.trysomething.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.danil.trysomething.entity.Event;

@Slf4j
@Component
public class KafkaProcessor {

    @KafkaListener(topics = "events",groupId = "try")
    public void onEvent(Event o) {
        log.info("Received: {}" , o);
        System.out.println(o);
    }

}
