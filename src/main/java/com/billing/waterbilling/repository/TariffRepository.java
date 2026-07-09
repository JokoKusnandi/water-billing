package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TariffRepository extends
        JpaRepository<Tariff,String>,
        JpaSpecificationExecutor<Tariff> {

}
