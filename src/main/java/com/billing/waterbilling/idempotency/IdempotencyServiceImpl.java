package com.billing.waterbilling.idempotency;

import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.entity.Payment;
import com.billing.waterbilling.exception.BillingNotFoundException;
import com.billing.waterbilling.exception.IdempotencyNotFoundException;
import com.billing.waterbilling.exception.PaymentNotFoundException;
import com.billing.waterbilling.repository.BillingRepository;
import com.billing.waterbilling.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class IdempotencyServiceImpl implements IdempotencyService {

    private final PaymentIdempotencyRepository repository;

    private final BillingRepository billingRepository;

    private final PaymentRepository paymentRepository;

    private final Clock clock;

    @Override
    public PaymentIdempotency register(
            String key,
            String billingId) {

        Billing billing = billingRepository
                .findByBillingId(billingId)
                .orElseThrow(BillingNotFoundException::new);
        PaymentIdempotency entity =
                PaymentIdempotency.builder()
                        .idempotencyKey(key)
                        .billing(billing)
                        .createdAt(LocalDateTime.now(clock))
                        .build();

        try {

            return repository.saveAndFlush(entity);

        } catch (DataIntegrityViolationException ex) {

            return repository.findByIdempotencyKey(key)
                    .orElseThrow(() -> ex);

        }

    }


    @Override
    @Transactional(readOnly = true)
    public PaymentIdempotency get(String key) {

        return repository.findByIdempotencyKey(key)

                .orElseThrow(
                        IdempotencyNotFoundException::new
                );

    }

    @Override
    public void bindPayment(
            String key,
            UUID  paymentId) {

        PaymentIdempotency entity = get(key);

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

        entity.setPayment(payment);

        repository.save(entity);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(String key) {

        return repository.existsByIdempotencyKey(key);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentIdempotency> find(String key){

        return repository.findByIdempotencyKey(key);

    }

    @Override
    public void complete(
            String key,
            UUID  paymentId) {

        PaymentIdempotency entity = get(key);

        Payment payment = paymentRepository
                .findById(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

        entity.setPayment(payment);

        repository.save(entity);

    }

    @Override
    @Transactional(readOnly = true)
    public  Optional<UUID> findPaymentId(
            String key) {

        return repository.findByIdempotencyKey(key)

                .map(PaymentIdempotency::getPayment)
                .map(Payment::getPaymentId);

    }


}
