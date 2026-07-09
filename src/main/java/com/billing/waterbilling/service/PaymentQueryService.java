package com.billing.waterbilling.service;

import com.billing.waterbilling.entity.Payment;

import java.util.UUID;

public interface PaymentQueryService {

    Payment findById(UUID paymentId);

    Payment findByBilling(String billingId);

}