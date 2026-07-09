package com.billing.waterbilling.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class PaymentNumberGenerator {

    private final AtomicInteger sequence = new AtomicInteger(1);

    public synchronized String generate() {

        String prefix = "PAY";

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int seq = sequence.getAndIncrement();

        if (seq > 9999) {
            sequence.set(1);
            seq = 1;
        }

        return prefix
                + timestamp
                + String.format("%04d", seq);
    }

}