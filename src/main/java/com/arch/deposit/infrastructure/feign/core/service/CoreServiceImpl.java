package com.arch.deposit.infrastructure.feign.core.service;

import com.arch.deposit.infrastructure.feign.core.CoreClient;
import com.arch.deposit.infrastructure.feign.core.dto.CoreDepositTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService{
    private final CoreClient coreClient;

    @Override
    public List<CoreDepositTypeDTO> getDepositTypes() {
        return coreClient.getDepositTypes();
    }
}
