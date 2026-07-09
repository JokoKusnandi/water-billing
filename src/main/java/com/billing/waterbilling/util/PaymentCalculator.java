package com.billing.waterbilling.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;

@Component
public class PaymentCalculator {

    private final Clock clock;

    public PaymentCalculator(Clock clock) {
        this.clock = clock;
    }

    public boolean isLatePayment(LocalDate dueDate) {
        return LocalDate.now(clock).isAfter(dueDate);
    }

    public BigDecimal calculatePenalty(
            BigDecimal amount,
            BigDecimal penaltyPercent,
            LocalDate dueDate) {

        if (!isLatePayment(dueDate)) {
            return BigDecimal.ZERO;
        }

        return amount.multiply(penaltyPercent);
    }

    public BigDecimal calculateTotal(
            BigDecimal amount,
            BigDecimal penalty) {

        return amount.add(penalty);
    }

}
