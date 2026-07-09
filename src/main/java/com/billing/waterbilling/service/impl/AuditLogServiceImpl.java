package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.entity.AuditLog;
import com.billing.waterbilling.entity.Payment;
import com.billing.waterbilling.repository.AuditLogRepository;
import com.billing.waterbilling.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl
        implements AuditLogService {

    private final AuditLogRepository repository;

    private final Clock clock;

    @Override
    public void paymentCreated(Payment payment) {

        AuditLog log = AuditLog.builder()
                .event("PAYMENT_CREATED")
                .referenceId(payment.getPaymentId().toString())
                .customerId(
                        payment.getBilling()
                                .getReading()
                                .getMeter()
                                .getCustomer()
                                .getCustomerId()
                )
                .username("SYSTEM")
                .ipAddress("127.0.0.1")
                .device("WATER-BILLING")
                .build();

        repository.save(log);
    }

}