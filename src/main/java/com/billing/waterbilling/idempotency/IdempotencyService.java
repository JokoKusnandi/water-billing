package com.billing.waterbilling.idempotency;

import java.util.Optional;
import java.util.UUID;

public interface IdempotencyService {

    PaymentIdempotency register(
            String key,
            String billingId);

    PaymentIdempotency get(String key);

    void bindPayment(
            String key,
            UUID paymentId
    );

    boolean exists(String key);

    Optional<PaymentIdempotency> find(String key);

    void complete(
            String idempotencyKey,
            UUID  paymentId
    );

    Optional<UUID> findPaymentId(
            String key);

}
