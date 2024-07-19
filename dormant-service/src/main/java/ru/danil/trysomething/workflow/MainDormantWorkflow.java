package ru.danil.trysomething.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface MainDormantWorkflow {

    @WorkflowMethod
    void execute();
}
