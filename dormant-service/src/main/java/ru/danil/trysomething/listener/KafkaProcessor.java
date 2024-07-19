package ru.danil.trysomething.listener;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.danil.trysomething.entity.Event;
import ru.danil.trysomething.exception.FastFailException;
import ru.danil.trysomething.workflow.IncWorkflow;

@Slf4j
@Component
public class KafkaProcessor {

    @Autowired
    WorkflowClient client;

    @KafkaListener(topics = "events", groupId = "dormant")
    public void onEvent(Event event) {
        log.info("Received: {}", event);
        IncWorkflow workflow =
                client.newWorkflowStub(
                        IncWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()
                                        // setting do not retry for FastFailException
                                        .setDoNotRetry(FastFailException.class.getName())
                                        .build())
                                .setTaskQueue("DormantTaskQueue")
                                .setWorkflowId("PotentialDormant")
                                .build());
        workflow.onMessage(event);
    }

}
