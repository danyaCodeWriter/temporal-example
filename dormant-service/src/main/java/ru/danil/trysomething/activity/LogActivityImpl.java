package ru.danil.trysomething.activity;

import io.temporal.spring.boot.ActivityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ActivityImpl(taskQueues = "DormantTaskQueue")
public class LogActivityImpl implements LogActivity {
    @Override
    public void sendMessageToLog(String message) {
        log.error(message);
    }
}
