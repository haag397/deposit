package com.arch.deposit.deposittype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface DepositTypeRepository extends JpaRepository<DepositType, UUID> {
    Optional<DepositType> findByName(String name);
    List<DepositType> findAllByNameIn(Collection<String> names);
    @Modifying
    @Query("delete from DepositType d where d.name not in :names")
    int deleteByNameNotIn(@Param("names") Set<String> names);
}
