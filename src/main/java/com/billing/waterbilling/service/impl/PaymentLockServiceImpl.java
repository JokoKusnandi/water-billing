package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.exception.BillingNotFoundException;
import com.billing.waterbilling.repository.BillingLockRepository;
import com.billing.waterbilling.service.PaymentLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentLockServiceImpl
        implements PaymentLockService {

    private final BillingLockRepository repository;

    @Override
    public Billing lockBilling(
            String billingId) {

        return repository.lockBilling(billingId)

                .orElseThrow(
                        BillingNotFoundException::new
                );

    }

}
