package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Meter;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface MeterRepository extends
        JpaRepository<Meter,Long>,
        JpaSpecificationExecutor<Meter> {

    @EntityGraph(attributePaths = {
            "customer",
            "tariff"
    })
    Optional<Meter> findByCustomerCustomerId(String customerId);

    boolean existsByCustomerCustomerId(String customerId);
    boolean existsBySerialNumber(String serialNumber);

}