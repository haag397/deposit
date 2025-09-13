package com.arch.deposit.service;

import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.arch.deposit.command.SelectDepositTypeCommand;
import com.arch.deposit.command.StartDepositOpeningCommand;

import io.camunda.zeebe.client.ZeebeClient;

/**
 * Application service orchestrating commands to Axon and messages to Camunda.
 */
@Service
public class DepositService {

    private final CommandGateway commandGateway;
    private final ZeebeClient zeebeClient;

    public DepositService(CommandGateway commandGateway,
                          ZeebeClient zeebeClient) {
        this.commandGateway = commandGateway;
        this.zeebeClient = zeebeClient;
    }

    /**
     * Starts the flow by creating the deposit aggregate and launching the BPMN
     * process instance.
     *
     * @return the generated deposit identifier
     */
    public String startDepositOpening(String userId) {
        String depositId = UUID.randomUUID().toString();

        commandGateway.sendAndWait(new StartDepositOpeningCommand(depositId, userId));

        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Process_182eqvi")
                .latestVersion()
                .variables(Map.of("userID", userId, "depositId", depositId))
                .send()
                .join();

        // Advance the workflow past the first catch event
        zeebeClient.newPublishMessageCommand()
                .messageName("start-deposit-opening")
                .correlationKey(depositId)
                .variables(Map.of("userID", userId, "depositId", depositId))
                .send()
                .join();

        return depositId;
    }

    /** Second step: user chooses a deposit type. */
    public void selectDepositType(String userId, String depositId, UUID depositTypeId) {
        commandGateway.sendAndWait(new SelectDepositTypeCommand(depositId, depositTypeId));

        zeebeClient.newPublishMessageCommand()
                .messageName("select-deposit-type")
                .correlationKey(depositId)
                .variables(Map.of("depositId", depositId, "depositTypeId", depositTypeId.toString()))
                .send()
                .join();
    }
}

