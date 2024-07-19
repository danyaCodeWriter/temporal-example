package ru.danil.trysomething.workflow;

import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import ru.danil.trysomething.activity.KafkaActivity;
import ru.danil.trysomething.activity.LogActivity;
import ru.danil.trysomething.activity.RepositoryActivity;
import ru.danil.trysomething.entity.Event;

import java.time.Duration;

@WorkflowImpl(taskQueues = "DormantTaskQueue")
@Slf4j
public class IncWorkflowImpl implements IncWorkflow {
    private KafkaActivity kafkaActivity =
            Workflow.newActivityStub(
                    KafkaActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    private RepositoryActivity repoActivity =
            Workflow.newActivityStub(
                    RepositoryActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    private LogActivity logActivity =
            Workflow.newLocalActivityStub(
                    LogActivity.class,
                    LocalActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    @Override
    public void onMessage(Event event) {
        repoActivity.saveEvent(event);
        try {
            kafkaActivity.sendMessage(event.toString());
        } catch (ActivityFailure e) {
            logActivity.sendMessageToLog(event.toString());
        }
    }
}
