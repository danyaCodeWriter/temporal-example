package ru.danil.trysomething.workflow;


import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import lombok.extern.slf4j.Slf4j;
import ru.danil.trysomething.activity.Activity;

import java.time.Duration;

@io.temporal.spring.boot.WorkflowImpl(taskQueues = "PermissionTaskQueue")
@Slf4j
public class WorkflowImpl implements Workflow {

    private Activity activity = io.temporal.workflow.Workflow.newActivityStub(
            Activity.class,
            ActivityOptions.newBuilder().setRetryOptions(RetryOptions.newBuilder()
                            .setMaximumAttempts(2).build())
                    .setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private boolean firstFlag = false;
    private boolean secondFlag = false;
    private boolean thirdFlag = false;

    @Override
    public void startWorkflow() {
        activity.startActivity();
        System.out.println("wait for activity first");
        io.temporal.workflow.Workflow.await(() -> firstFlag );

        System.out.println("wait for activity second");
        io.temporal.workflow.Workflow.await(() -> secondFlag);

        System.out.println("wait for activity third");
        io.temporal.workflow.Workflow.await(() -> thirdFlag);
    }

    @Override
    public void firstSignal() {
        activity.firstActivity();
        firstFlag = true;
    }

    @Override
    public void secondSignal() {
        activity.secondActivity();
        secondFlag = true;
    }

    @Override
    public void thirdSignal() {
        activity.thirdActivity();
        thirdFlag = true;
    }
}
