package ru.danil.trysomething.workflow;


import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import ru.danil.trysomething.activity.KafkaActivity;
import ru.danil.trysomething.activity.PaymentActivity;
import ru.danil.trysomething.activity.RepositoryActivity;
import ru.danil.trysomething.activity.SetStatusActivity;
import ru.danil.trysomething.client.CheckDto;
import ru.danil.trysomething.entity.Event;
import ru.danil.trysomething.exception.FastFailException;

import java.time.Duration;
import java.util.List;
import java.util.random.RandomGenerator;

@WorkflowImpl(taskQueues = "DormantTaskQueue")
@Slf4j
public class MainDormantWorkflowImpl implements MainDormantWorkflow {

    private RepositoryActivity repoActivity =
            Workflow.newActivityStub(
                    RepositoryActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    private KafkaActivity kafkaActivity =
            Workflow.newActivityStub(
                    KafkaActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    private PaymentActivity paymentActivity =
            Workflow.newActivityStub(
                    PaymentActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setDoNotRetry(FastFailException.class.getName())
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    private SetStatusActivity statusActivity =
            Workflow.newActivityStub(
                    SetStatusActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setDoNotRetry(FastFailException.class.getName())
                                    .setMaximumAttempts(2).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());

    @Override
    public void execute() {
        try {
            List<Event> eventsForProcessing = repoActivity.getEventsForProcessing();
            if (eventsForProcessing.isEmpty()) {
                return;
            }
            kafkaActivity.sendMessage(String.format("Получили n: %d эвентов для процессинга", eventsForProcessing.size()));
            CheckDto message = new CheckDto();
            RandomGenerator aDefault = RandomGenerator.getDefault();
            message.setAmount(aDefault.nextLong(1000));
            message.setFromId(aDefault.nextLong(120));
            message.setToId(aDefault.nextLong(120));
            paymentActivity.deposit(message);
            paymentActivity.withdraw(message);
            statusActivity.setStatusToBlocked(eventsForProcessing.get(0).getUserId());
        } catch (ActivityFailure e) {
            log.info("\n**** message: " + e.getMessage());
            log.info("\n**** cause: " + e.getCause().getClass().getName());
            log.info("\n**** cause message: " + e.getCause().getMessage());
            if (e.getCause().getCause() != null) {
                log.info("\n**** cause->cause: message: " + e.getCause().getCause().getMessage());
            }
            throw new FastFailException("разобрали ошибки, ничего не сделать.");
        }
    }
}
