package com.arch.deposit.service;

import com.arch.deposit.api.dto.FinalConfirmReq;
import com.arch.deposit.command.deposit.CreateDepositCommand;
import com.arch.deposit.infrastructure.feign.core.dto.CreateDepositWithTransferReq;
import com.arch.deposit.infrastructure.feign.core.service.CoreService;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositAppService {

    private final ZeebeClient zeebe;
    private final CommandGateway commandGateway;
    private final CoreService core;

    // Step 0
    public String startSession(String userId, String customerNumber) {
        String depositId = UUID.randomUUID().toString();
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId("opening-deposit")
                .latestVersion()
                .variables(Map.of("userId", userId, "customerNumber", customerNumber, "depositId", depositId))
                .send()
                .join();
        return depositId;
    }

    // Step 1
//    public void selectDepositType(String userId, String depositId, UUID typeId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("select deposit type")
//                .correlationKey(depositId)
//                .variables(Map.of("userId", userId, "depositTypeId", typeId.toString()))
//                .send()
//                .join();
//    }
//
//    // Step 2
//    public void startDepositOpening(String userId, String depositId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("start deposit")
//                .correlationKey(depositId)
//                .variables(Map.of("userId", userId))
//                .send()
//                .join();
//    }

    // Step 3 (gateway drives on `accepted`)
    public void acceptRules(String userId, String depositId, boolean accepted) {
        zeebe.newPublishMessageCommand()
                .messageName("Rules Acceptance")
                .correlationKey(depositId)
                .variables(Map.of("accepted", accepted))
                .send()
                .join();
    }

//    public void continueAfterAccept(String userId, String depositId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("continue after accept rule")
//                .correlationKey(depositId)
//                .variables(Map.of())
//                .send()
//                .join();
//    }
//
//    public void selectDeposit(String userId, String depositId, SelectDepositReq req) {
//        zeebe.newPublishMessageCommand()
//                .messageName("Select deposit")
//                .correlationKey(depositId)
//                .variables(Map.of())
//                .send()
//                .join();
//    }
//
//    public void continueAfterSelect(String userId, String depositId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("continue after select deposit")
//                .correlationKey(depositId)
//                .variables(Map.of())
//                .send()
//                .join();
//    }
//
//    public void enterAmount(String userId, String depositId, BigDecimal amount) {
//        zeebe.newPublishMessageCommand()
//                .messageName("Entered the deposit opening amount")
//                .correlationKey(depositId)
//                .variables(Map.of("amount", amount))
//                .send()
//                .join();
//    }
//
//    public void confirmAmount(String userId, String depositId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("confirm amount")
//                .correlationKey(depositId)
//                .variables(Map.of())
//                .send()
//                .join();
//    }

//    public void chooseProfitDestination(String userId, String depositId, ProfitDestinationReq req) {
//        zeebe.newPublishMessageCommand()
//                .messageName("choose destination deposit of profit")
//                .correlationKey(depositId)
//                .variables(Map.of(
//                        "destinationType", req.destinationType(),
//                        "destinationAccount", req.destinationAccount()
//                ))
//                .send()
//                .join();
//    }

//    public void continueAfterChooseDestination(String userId, String depositId) {
//        zeebe.newPublishMessageCommand()
//                .messageName("continue after choose destination deposit")
//                .correlationKey(depositId)
//                .variables(Map.of())
//                .send()
//                .join();
//    }

    // Final step: write domain state then nudge BPMN to end
    public void confirmInfoAndOpen(FinalConfirmReq req) {
        var coreResp = core.createDepositSmart(new CreateDepositWithTransferReq(
                req.deposType(),
                req.currency(),
                req.amount(),
                req.sourceDeposit(),
                req.destDeposit(),
                req.customerNumber(),
                req.currentBranchCode()
        ));

        var http = coreResp.status();
        Integer statusCode = (http != null ? http.code() : null);
        String statusMessage = (http != null ? http.message() : null);

        var result = coreResp.result();
        var data = (result != null) ? result.data() : null;
        var meta = coreResp.meta();

        commandGateway.sendAndWait(new CreateDepositCommand(
                req.depositId(),
                req.userId(),

                statusCode,
                statusMessage,

                data != null ? data.transactionDate() : null,
                data != null ? data.depositNumber() : null,
                data != null ? data.currentAmount() : null,
                data != null ? data.currentWithdrawableAmount() : null,
                meta != null ? meta.transactionId() : null,

                // pass request values so projection can save them on SUCCESS
                req.deposType(),
                req.customerNumber()
        ));

        if (Objects.equals(statusCode, 200)) {
            zeebe.newPublishMessageCommand()
                    .messageName("Confirm information and open a deposit")
                    .correlationKey(req.depositId())
                    .variables(Map.of("opened", true))
                    .send()
                    .join();
        }
    }
}