package com.billing.waterbilling.service;

import com.billing.waterbilling.entity.Billing;

public interface PaymentLockService {

    Billing lockBilling(
            String billingId
    );

}