package ru.danil.trysomething.service;


import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.danil.trysomething.workflow.Workflow;

@Service
@RequiredArgsConstructor
public class PermitService {
    private final WorkflowClient client;

    public void firstPermit(String id) {
        Workflow workflow = client.newWorkflowStub(Workflow.class, "PermissionFlow" + id);
        workflow.firstSignal();
    }

    public void secondPermit(String id) {
        Workflow workflow = client.newWorkflowStub(Workflow.class, "PermissionFlow" + id);
        workflow.secondSignal();
    }

    public void thirdPermit(String id) {
        Workflow workflow = client.newWorkflowStub(Workflow.class, "PermissionFlow" + id);
        workflow.thirdSignal();
    }

    public void startWorkflow(String id) {
        Workflow workflow =
                client.newWorkflowStub(
                        Workflow.class,
                        WorkflowOptions.newBuilder()
                                .setRetryOptions(RetryOptions.newBuilder()
                                        // setting do not retry for FastFailException
                                        .build())
                                .setTaskQueue("PermissionTaskQueue")
                                //уникальный воркфлоу в один момент времени
                                .setWorkflowId("PermissionFlow" + id)
                                .build());
        WorkflowClient.start(workflow::startWorkflow);
    }
}
