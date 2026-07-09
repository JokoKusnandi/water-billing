package com.billing.waterbilling.service;

import com.billing.waterbilling.entity.Payment;

public interface AuditLogService {

    void paymentCreated(
            Payment payment
    );

}
