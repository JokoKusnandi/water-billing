package com.billing.waterbilling.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentIdempotencyRepository
        extends JpaRepository<PaymentIdempotency, Long> {

    Optional<PaymentIdempotency> findByIdempotencyKey(String key);

    Optional<PaymentIdempotency> findByPaymentPaymentId(UUID paymentId);

    Optional<PaymentIdempotency> findByBillingBillingId(String billingId);

    boolean existsByIdempotencyKey(String key);

}
