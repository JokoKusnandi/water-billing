package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Payment;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
public interface PaymentRepository extends
        JpaRepository<Payment, UUID>,
        JpaSpecificationExecutor<Payment> {

    Optional<Payment> findByPaymentId(UUID paymentId);

    @EntityGraph(attributePaths = {
            "billing",
            "billing.reading",
            "billing.reading.meter",
            "billing.reading.meter.customer",
            "billing.reading.meter.tariff"
    })
    Optional<Payment> findByBillingBillingId(String billingId);

    boolean existsByBillingBillingId(String billingId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("""
        select p
        from Payment p
        join fetch p.billing b
        join fetch b.reading r
        join fetch r.meter m
        join fetch m.customer
        join fetch m.tariff
        where p.paymentId = :paymentId
        """)
    Optional<Payment> findDetailByPaymentId(@Param("paymentId") UUID paymentId);

}