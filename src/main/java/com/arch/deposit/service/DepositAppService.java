package com.arch.deposit.service;

import com.arch.deposit.api.dto.FinalConfirmReq;
import com.arch.deposit.api.dto.ProfitDestinationReq;
import com.arch.deposit.api.dto.SelectDepositReq;
import com.arch.deposit.command.CreateDepositCommand;
import com.arch.deposit.infrastructure.feign.core.dto.CoreCreateDepositResponse;
import com.arch.deposit.infrastructure.feign.core.dto.CreateDepositSimpleReq;
import com.arch.deposit.infrastructure.feign.core.dto.CreateDepositWithTransferReq;
import com.arch.deposit.infrastructure.feign.core.service.CoreService;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;
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
                .bpmnProcessId("deposit-opening")
                .latestVersion()
                .variables(Map.of("userId", userId,"customerNumber", customerNumber, "depositId", depositId))
                .send()
                .join();
        return depositId;
    }

    // Step 1
    public void selectDepositType(String userId, String depositId, UUID typeId) {
        zeebe.newPublishMessageCommand()
                .messageName("select deposit type")
                .correlationKey(depositId)
                .variables(Map.of("userId", userId, "depositTypeId", typeId.toString()))
                .send()
                .join();
    }

    // Step 2
    public void startDepositOpening(String userId, String depositId) {
        zeebe.newPublishMessageCommand()
                .messageName("start deposit")
                .correlationKey(depositId)
                .variables(Map.of("userId", userId))
                .send()
                .join();
    }

    // Step 3 (gateway drives on `accepted`)
    public void acceptRules(String userId, String depositId, boolean accepted) {
        zeebe.newPublishMessageCommand()
                .messageName("accept rules one")
                .correlationKey(depositId)
                .variables(Map.of("accepted", accepted))
                .send()
                .join();
    }

    public void continueAfterAccept(String userId, String depositId) {
        zeebe.newPublishMessageCommand()
                .messageName("continue after accept rule")
                .correlationKey(depositId)
                .variables(Map.of())
                .send()
                .join();
    }

    public void selectDeposit(String userId, String depositId, SelectDepositReq req) {
        zeebe.newPublishMessageCommand()
                .messageName("Select deposit")
                .correlationKey(depositId)
                .variables(Map.of())
                .send()
                .join();
    }

    public void continueAfterSelect(String userId, String depositId) {
        zeebe.newPublishMessageCommand()
                .messageName("continue after select deposit")
                .correlationKey(depositId)
                .variables(Map.of())
                .send()
                .join();
    }

    public void enterAmount(String userId, String depositId, BigDecimal amount) {
        zeebe.newPublishMessageCommand()
                .messageName("Entered the deposit opening amount")
                .correlationKey(depositId)
                .variables(Map.of("amount", amount))
                .send()
                .join();
    }

    public void confirmAmount(String userId, String depositId) {
        zeebe.newPublishMessageCommand()
                .messageName("confirm amount")
                .correlationKey(depositId)
                .variables(Map.of())
                .send()
                .join();
    }

    public void chooseProfitDestination(String userId, String depositId, ProfitDestinationReq req) {
        zeebe.newPublishMessageCommand()
                .messageName("choose destination deposit of profit")
                .correlationKey(depositId)
                .variables(Map.of(
                        "destinationType", req.destinationType(),
                        "destinationAccount", req.destinationAccount()
                ))
                .send()
                .join();
    }

    public void continueAfterChooseDestination(String userId, String depositId) {
        zeebe.newPublishMessageCommand()
                .messageName("continue after choose destination deposit")
                .correlationKey(depositId)
                .variables(Map.of())
                .send()
                .join();
    }

    // Final step: write domain state then nudge BPMN to end

    public void confirmInfoAndOpen(FinalConfirmReq req) {
        // 1) Call Core
        CoreCreateDepositResponse coreResp;
        if (req.sourceDeposit() != null || req.destDeposit() != null) {
            coreResp = core.createDepositWithInterest(new CreateDepositWithTransferReq(
                    req.deposType(),
                    req.currency(),
                    req.amount().toPlainString(),     // Core expects string
                    req.sourceDeposit(),
                    req.destDeposit(),
                    req.customerNumber(),
                    req.currentBranchCode()
            ));
        } else {
            coreResp = core.createDeposit(new CreateDepositSimpleReq(
                    req.deposType(),
                    req.currency(),
                    req.amount().toPlainString(),
                    req.customerNumber(),
                    req.currentBranchCode()
            ));
        }

        // 2) Check Core status and extract data
        var http = coreResp.status();
        if (http == null || http.code() == null || http.code() != 200) {
            var code = (http != null ? http.code() : null);
            var msg  = (http != null ? http.message() : "Unknown error");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Core deposit creation failed (httpCode=" + code + "): " + msg);
        }

        var data = coreResp.result() != null ? coreResp.result().data() : null;
        if (data == null || data.depositNumber() == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Core returned no deposit data");
        }

        // 3) Domain write via Axon
        commandGateway.sendAndWait(new CreateDepositCommand(
                req.depositId(),
                req.userId(),
                data.depositNumber(),
                data.iban(),
                data.currentAmount(),
                data.currentWithdrawableAmount()
        ));

        // 4) Nudge BPMN to end
        zeebe.newPublishMessageCommand()
                .messageName("Confirm information and open a deposit")
                .correlationKey(req.depositId())
                .variables(Map.of("opened", true))
                .send()
                .join();
    }
}