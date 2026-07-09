package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.entity.Payment;
import com.billing.waterbilling.exception.PaymentNotFoundException;
import com.billing.waterbilling.repository.PaymentRepository;
import com.billing.waterbilling.service.PaymentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentQueryServiceImpl
        implements PaymentQueryService {

    private final PaymentRepository repository;

    @Override
    public Payment findById(
            UUID paymentId) {

        return repository.findDetailByPaymentId(paymentId)

                .orElseThrow(
                        PaymentNotFoundException::new);

    }

    @Override
    public Payment findByBilling(
            String billingId) {

        return repository.findByBillingBillingId(billingId)

                .orElseThrow(
                        PaymentNotFoundException::new);

    }

}