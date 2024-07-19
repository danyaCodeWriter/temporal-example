package ru.danil.trysomething.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import ru.danil.trysomething.dto.CheckDto;
import ru.danil.trysomething.entity.Check;

@WorkflowInterface
public interface MainPaymentWorkflow {

    @WorkflowMethod
    boolean execute(CheckDto check);
}
