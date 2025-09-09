package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.CoreClient;
import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService{
    private final CoreClient coreClient;

    @Override
    public List<CoreDepositTypeResponseDTO> getDepositTypes() {
        return coreClient.getDepositTypes();
    }
}
