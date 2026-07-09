package com.billing.waterbilling.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BillingCalculator {

    public BigDecimal usage(BigDecimal previous,
                            BigDecimal current){

        return current.subtract(previous);

    }

    public BigDecimal amount(BigDecimal usage,
                             BigDecimal price){

        return usage.multiply(price);

    }

    public BigDecimal penalty(BigDecimal amount,
                              BigDecimal percent){

        return amount.multiply(percent);

    }

}
