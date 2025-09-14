//package com.arch.deposit.service;
//
//import java.util.Map;
//import java.util.UUID;
//
//import lombok.RequiredArgsConstructor;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.springframework.stereotype.Service;
//
//import com.arch.deposit.command.SelectDepositTypeCommand;
//import com.arch.deposit.command.StartDepositOpeningCommand;
//
//import io.camunda.zeebe.client.ZeebeClient;
//
///**
// * Application service orchestrating commands to Axon and messages to Camunda.
// */
//@Service
//@RequiredArgsConstructor
//public class DepositService {
//
//    private final CommandGateway commandGateway;
//    private final ZeebeClient zeebeClient;
//
//
//    public String startSession(String userId) {
//        String depositId = UUID.randomUUID().toString();
//        zeebeClient.newCreateInstanceCommand()
//                .bpmnProcessId("deposit-opening")
//                .latestVersion()
//                .variables(Map.of("userId", userId, "depositId", depositId))
//                .send()
//                .join();
//        return depositId;
//    }
//
//    // Step 1 – select type (publish to "select deposit type")
//    public void selectDepositType(String userId, String depositId, UUID depositTypeId) {
//        commandGateway.sendAndWait(new SelectDepositTypeCommand(depositId, userId, depositTypeId));
//
//        zeebeClient.newPublishMessageCommand()
//                .messageName("select deposit type")       // <-- EXACT name from BPMN
//                .correlationKey(depositId)                // <-- uses depositId (matches =depositId)
//                .variables(Map.of(
//                        "depositId", depositId,               // optional but handy
//                        "depositTypeId", depositTypeId.toString()
//                ))
//                .send().join();
//    }
//    // Step 2 – confirm start (publish to "start deposit")
//    public void confirmStartOpening(String userId, String depositId) {
//        zeebeClient.newPublishMessageCommand()
//                .messageName("start deposit")             // <-- EXACT
//                .correlationKey(depositId)
//                .variables(Map.of("depositId", depositId, "confirm", true))
//                .send().join();
//    }
//
//    // Step 3 – accept terms (publish to "accept rules one")
//    public void acceptTerms(String userId, String depositId) {
//        // Domain: now really start it
//        commandGateway.sendAndWait(new StartDepositOpeningCommand(depositId));
//
//        zeebeClient.newPublishMessageCommand()
//                .messageName("accept rules one")          // <-- EXACT
//                .correlationKey(depositId)
//                .variables(Map.of("accepted", true))
//                .send().join();
//    }
//}