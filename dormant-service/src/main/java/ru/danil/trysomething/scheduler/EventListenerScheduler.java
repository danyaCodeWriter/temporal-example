package ru.danil.trysomething.scheduler;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.danil.trysomething.exception.FastFailException;
import ru.danil.trysomething.workflow.IncWorkflow;
import ru.danil.trysomething.workflow.MainDormantWorkflow;

import java.time.Duration;

@Component
@Slf4j
public class EventListenerScheduler {

    @Autowired
    WorkflowClient client;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MainDormantWorkflow workflow =
                client.newWorkflowStub(
                        MainDormantWorkflow.class,
                        WorkflowOptions.newBuilder()

//                                .setWorkflowExecutionTimeout(Duration.ofMinutes(1))
                                .setWorkflowRunTimeout(Duration.ofSeconds(10))
                                .setRetryOptions(RetryOptions.newBuilder()
                                                .setMaximumAttempts(2)

                                        // setting do not retry for FastFailException
                                        .setDoNotRetry(FastFailException.class.getName())
                                        .build())
                                .setTaskQueue("DormantTaskQueue")
                                .setWorkflowId("MainDormantWorkflow")
                                .setCronSchedule("@every 30s")
                                .build());
        WorkflowClient.execute(workflow::execute);
    }
}