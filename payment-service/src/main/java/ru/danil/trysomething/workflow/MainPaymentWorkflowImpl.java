package ru.danil.trysomething.workflow;


import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import ru.danil.trysomething.activity.RepositoryActivity;
import ru.danil.trysomething.dto.CheckDto;

import java.time.Duration;

@WorkflowImpl(taskQueues = "PaymentTaskQueue")
@Slf4j
public class MainPaymentWorkflowImpl implements MainPaymentWorkflow {

    private final RepositoryActivity repoActivity =
            Workflow.newActivityStub(
                    RepositoryActivity.class,
                    ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                                    .setMaximumAttempts(5).build())
                            .setStartToCloseTimeout(Duration.ofSeconds(3)).build());


    @Override
    public boolean execute(CheckDto check) {
        Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        Saga saga = new Saga(sagaOptions);
        try {
            Promise<Boolean> function = Async.function(repoActivity::saveCheck, check);
            CheckDto newCheck = new CheckDto();
            newCheck.setFromId(check.getToId());
            newCheck.setToId(check.getFromId());
            saga.addCompensation(repoActivity::saveCheck, newCheck);
            Boolean aBoolean = function.get();
            log.error("execute workflow one more!");
            //Workflow.sleep(100000);
            return aBoolean;
        } catch (ActivityFailure e) {
            saga.compensate();
            throw e;
        }
    }
}
