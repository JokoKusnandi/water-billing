package com.billing.waterbilling.constants;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {

    private static final AtomicLong BILLING_COUNTER = new AtomicLong(1);

    private IdGenerator() {
    }

    public static String generateBillingId() {
        return "BL%06d".formatted(BILLING_COUNTER.getAndIncrement());
    }

    public static UUID generatePaymentId() {
        return UUID.randomUUID();
    }
}
