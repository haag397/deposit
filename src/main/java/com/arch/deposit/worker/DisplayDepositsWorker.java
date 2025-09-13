package com.arch.deposit.worker;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.arch.deposit.account.DepositRepository;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

/**
 * Worker invoked by the BPMN service task "Display user deposits". It returns
 * the user's deposit accounts from the local database for presentation.
 */
@Component
public class DisplayDepositsWorker {

    private final DepositRepository repository;

    public DisplayDepositsWorker(DepositRepository repository) {
        this.repository = repository;
    }

    @JobWorker(type = "Display user deposits")
    public Map<String, Object> handle(final ActivatedJob job) {
        String userId = (String) job.getVariablesAsMap().get("userId");
        return Map.of("deposits", repository.findByMainCustomerNumber(userId));
    }
}

