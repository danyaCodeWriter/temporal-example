package ru.danil.trysomething.service;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowException;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.danil.trysomething.dto.CheckDto;
import ru.danil.trysomething.exception.FastFailException;
import ru.danil.trysomething.repository.CheckRepository;
import ru.danil.trysomething.workflow.MainPaymentWorkflow;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final CheckRepository repository;
    private final WorkflowClient client;

    public Boolean deposit(CheckDto check) {
        MainPaymentWorkflow workflow =
                client.newWorkflowStub(
                        MainPaymentWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()

                                        // setting do not retry for FastFailException
                                        .setDoNotRetry(FastFailException.class.getName())
                                        .build())
                                .setTaskQueue("PaymentTaskQueue")
                                .setWorkflowExecutionTimeout(Duration.ofSeconds(30))
                                //уникальный воркфлоу в один момент времени
                                .setWorkflowId("Payment")
                                .build());
//        workflow.execute(check);
        CompletableFuture<Boolean> execute = WorkflowClient.execute(workflow::execute, check);
        try {
            return execute.get();
        } catch (WorkflowException e) {
            return false;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public Boolean withdraw(CheckDto check) {
        MainPaymentWorkflow workflow =
                client.newWorkflowStub(
                        MainPaymentWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()
                                        .setMaximumAttempts(2)
                                        // setting do not retry for FastFailException
                                        .setDoNotRetry(FastFailException.class.getName())
                                        .build())
                                .setTaskQueue("PaymentTaskQueue")
//                                .setWorkflowId("Payment")
                                .build());
        try {
            return workflow.execute(check);
        } catch (WorkflowException e) {
            return false;
        }
    }
}
