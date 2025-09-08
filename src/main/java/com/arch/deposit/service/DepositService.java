package com.arch.deposit.service;

import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.arch.deposit.command.SelectDepositTypeCommand;
import com.arch.deposit.command.StartDepositOpeningCommand;
import com.arch.deposit.projection.DepositView;
import com.arch.deposit.projection.DepositViewRepository;

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
     * First step: user selects a deposit type. We create the deposit aggregate,
     * start a BPMN process instance, and publish the message that moves the
     * process to the next step.
     *
     * @return the generated deposit identifier
     */
    public String selectDepositType(String userId, UUID depositTypeId) {
        String depositId = UUID.randomUUID().toString();

        commandGateway.sendAndWait(new SelectDepositTypeCommand(depositId, userId, depositTypeId));

        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Process_182eqvi")
                .latestVersion()
                .variables(Map.of("userID", userId, "depositId", depositId))
                .send()
                .join();

        zeebeClient.newPublishMessageCommand()
                .messageName("select-deposit-type")
                .correlationKey(userId)
                .variables(Map.of("depositId", depositId, "depositTypeId", depositTypeId.toString()))
                .send()
                .join();

        return depositId;
    }

    /** Third step: user confirms starting the deposit opening. */
    public void startDepositOpening(String userId, String depositId) {
        commandGateway.sendAndWait(new StartDepositOpeningCommand(depositId));

        zeebeClient.newPublishMessageCommand()
                .messageName("start-deposit-opening")
                .correlationKey(userId)
                .variables(Map.of("depositId", depositId))
                .send()
                .join();
    }
}