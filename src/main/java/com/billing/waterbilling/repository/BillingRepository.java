package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.enums.BillingStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

public interface BillingRepository extends
        JpaRepository<Billing,String>,
        JpaSpecificationExecutor<Billing> {

    @EntityGraph(attributePaths = {
            "reading",
            "reading.meter",
            "reading.meter.customer",
            "reading.meter.tariff"
    })
    Optional<Billing> findByBillingId(String billingId);

    @EntityGraph(attributePaths = {
            "reading",
            "reading.meter",
            "reading.meter.customer",
            "reading.meter.tariff",
            "payment"
    })
    Optional<Billing>
    findByReadingMeterCustomerCustomerIdAndReadingPeriod(
            String customerId,
            String period
    );

    List<Billing>
    findByStatus(BillingStatus status);

    @EntityGraph(attributePaths = {
            "reading",
            "reading.meter",
            "reading.meter.customer",
            "reading.meter.tariff"
    })
    Page<Billing>
    findByReadingMeterCustomerCustomerIdOrderByReadingPeriodDesc(
            String customerId,
            Pageable pageable
    );

    boolean existsByReadingMeterCustomerCustomerIdAndReadingPeriod(
            String customerId,
            String period
    );

}
