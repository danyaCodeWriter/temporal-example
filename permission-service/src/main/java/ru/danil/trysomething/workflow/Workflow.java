package ru.danil.trysomething.workflow;

import io.temporal.activity.ActivityMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface Workflow {
    @WorkflowMethod
    void startWorkflow();
    @SignalMethod
    void firstSignal();
    @SignalMethod
    void secondSignal();
    @SignalMethod
    void thirdSignal();
}
