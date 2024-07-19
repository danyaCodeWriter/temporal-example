package ru.danil.trysomething.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import ru.danil.trysomething.entity.Event;

@WorkflowInterface
public interface IncWorkflow {

    @WorkflowMethod
    void onMessage(Event event);

}
