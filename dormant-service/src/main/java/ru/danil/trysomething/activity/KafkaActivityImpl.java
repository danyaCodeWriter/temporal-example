/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package ru.danil.trysomething.activity;

import io.temporal.client.WorkflowException;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@ActivityImpl(taskQueues = "DormantTaskQueue")
public class KafkaActivityImpl implements KafkaActivity {

    // Setting required to false means we won't fail
    // if a test does not have kafka enabled
    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${meta.message.topic.name}")
    private String topicName;

    @Override
    public void sendMessage(String message) {
        try {
            kafkaTemplate.send(topicName, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw ApplicationFailure.newNonRetryableFailureWithCause(
                    "Unable to send message.", e.getClass().getName(), e.getCause());
        }
    }
}
