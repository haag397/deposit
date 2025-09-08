package com.arch.deposit.worker;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.arch.deposit.deposittype.DepositTypeRepository;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

/**
 * Worker invoked by the BPMN service task "Display user deposits". It now
 * returns the available deposit types so the UI can present them for selection.
 */
@Component
public class DisplayDepositsWorker {

    private final DepositTypeRepository repository;

    public DisplayDepositsWorker(DepositTypeRepository repository) {
        this.repository = repository;
    }

    @JobWorker(type = "display-user-deposits")
    public Map<String, Object> handle(final ActivatedJob job) {
        // return all available deposit types for selection
        return Map.of("depositTypes", repository.findAll());
    }
}

