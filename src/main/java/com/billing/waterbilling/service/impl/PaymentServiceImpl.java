package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.request.PaymentRequest;
import com.billing.waterbilling.dto.response.PaymentResponse;
import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.entity.Payment;

import com.billing.waterbilling.enums.BillingStatus;
import com.billing.waterbilling.exception.ConcurrentPaymentException;
import com.billing.waterbilling.exception.InvalidRequestException;
import com.billing.waterbilling.exception.PaymentNotFoundException;
import com.billing.waterbilling.idempotency.IdempotencyService;
import com.billing.waterbilling.idempotency.PaymentIdempotency;
import com.billing.waterbilling.idempotency.PaymentIdempotencyRepository;
import com.billing.waterbilling.mapper.PaymentMapper;
import com.billing.waterbilling.repository.BillingRepository;
import com.billing.waterbilling.repository.PaymentRepository;
import com.billing.waterbilling.service.*;
import com.billing.waterbilling.util.PaymentCalculator;
import com.billing.waterbilling.util.PaymentNumberGenerator;
import com.billing.waterbilling.validator.BusinessValidator;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(
        rollbackFor = Exception.class
)
public class PaymentServiceImpl implements PaymentService {

    /*
     * ---------------------------------------------------------
     * Repository
     * ---------------------------------------------------------
     */

    private final PaymentRepository paymentRepository;

    private final BillingRepository billingRepository;

    private final PaymentIdempotencyRepository paymentIdempotencyRepository;

    /*
     * ---------------------------------------------------------
     * Mapper
     * ---------------------------------------------------------
     */

    private final PaymentMapper paymentMapper;

    /*
     * ---------------------------------------------------------
     * Calculator
     * ---------------------------------------------------------
     */

    private final PaymentCalculator paymentCalculator;

    /*
     * ---------------------------------------------------------
     * Utility
     * ---------------------------------------------------------
     */

    private final Clock clock;

    private final PaymentNumberGenerator
            paymentNumberGenerator;

    /*
     * ---------------------------------------------------------
     * Service
     * ---------------------------------------------------------
     */

    private final ReceiptService receiptService;

    private final AuditLogService auditLogService;

    private final IdempotencyService idempotencyService;

    private final PaymentLockService paymentLockService;

    private final PaymentQueryService paymentQueryService;

    /*
     * ---------------------------------------------------------
     * Validator
     * ---------------------------------------------------------
     */

    private final BusinessValidator validator;

    /*
     * ---------------------------------------------------------
     * Optional
     * ---------------------------------------------------------
     */

    private final EntityManager entityManager;

    /**
     * =========================================================
     * PAYMENT
     * =========================================================
     */

    @Override
    public PaymentResponse pay(PaymentRequest request) {

        try{

            // seluruh business logic

            validateRequest(request);

            PaymentIdempotency idempotency =
                    idempotencyService.register(
                            request.getIdempotencyKey(),
                            request.getBillingId()
                    );

            /*
             * STEP 3
             * Retry Safety
             */

            if (isRetryRequest(idempotency)) {

                log.info(
                        "Retry request detected : {}",
                        idempotency.getPayment().getPaymentId()
                );

                return buildResponse(

                        paymentQueryService.findById(

                                idempotency.getPayment().getPaymentId()

                        )

                );

            }

            Billing billing =

                    paymentLockService.lockBilling(

                            request.getBillingId()

                    );

            validator.validateBillingStatus(
                    billing.getStatus()
            );

            if(validateAlreadyPaid(billing)){

                return handleExistingPayment(
                        request,
                        billing
                );

            }

            BigDecimal penalty =
                    calculatePenalty(billing);

            BigDecimal total =
                    calculateTotal(
                            billing,
                            penalty
                    );

            Payment payment =
                    createPayment(
                            billing,
                            penalty,
                            total
                    );

            updateBilling(billing);

            saveIdempotency(
                    request,
                    payment
            );

            audit(
                    payment
            );

            flushTransaction();

            return buildResponse(
                    payment
            );

        }

        catch(ObjectOptimisticLockingFailureException ex){

            log.error(
                    "Optimistic Lock",
                    ex
            );

            throw new ConcurrentPaymentException();

        } catch (CannotAcquireLockException ex) {

            log.error(
                    "Cannot Acquire Lock",
                    ex
            );

            throw new ConcurrentPaymentException();

        }catch (PessimisticLockingFailureException ex) {

            log.error(
                    "Lock Timeout",
                    ex
            );

            throw new ConcurrentPaymentException();

        } catch (DataIntegrityViolationException ex) {

            log.error(
                    "Duplicate Payment",
                    ex
            );

            Payment payment =
                    paymentQueryService.findByBilling(
                            request.getBillingId()
                    );

            return buildResponse(payment);

        } catch (Exception ex) {

            log.error(
                    "Payment Failed",
                    ex
            );

            throw ex;

        }


    }

    /**
     * =========================================================
     * FIND PAYMENT
     * =========================================================
     */

    private boolean isRetryRequest(
            PaymentIdempotency entity) {

        return entity.getPayment().getPaymentId() != null;

    }

    private Payment createPayment(
            Billing billing,
            BigDecimal penalty,
            BigDecimal total) {

        log.debug(
                "Create Payment Entity"
        );

        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .paymentNumber(paymentNumberGenerator.generate())
                .billing(billing)
                .payDatetime(
                        LocalDateTime.now(clock))
                .penalty(penalty)
                .amountTotal(total)
                .build();

        Payment saved = paymentRepository.save(payment);

        entityManager.flush();
        return saved;

    }

    private void updateBilling(
            Billing billing) {

        log.debug(
                "Update Billing Status"
        );

        billing.setStatus(
                BillingStatus.PAID
        );

        billingRepository.save(billing);

    }

    private BigDecimal calculatePenalty(
            Billing billing) {

        return paymentCalculator.calculatePenalty(

                billing.getAmount(),

                billing.getReading()

                        .getMeter()

                        .getTariff()

                        .getPenaltyPercent(),

                billing.getDueDate()

        );

    }

    private BigDecimal calculateTotal(
            Billing billing,
            BigDecimal penalty) {

        return paymentCalculator.calculateTotal(

                billing.getAmount(),

                penalty

        );

    }

    private PaymentResponse buildResponse(
            Payment payment) {

        return paymentMapper.toResponse(
                payment
        );

    }

    private void saveIdempotency(
            PaymentRequest request,
            Payment payment) {

        idempotencyService.complete(

                request.getIdempotencyKey(),

                payment.getPaymentId()

        );

    }

    private void flushTransaction() {

        entityManager.flush();

        log.debug(
                "Persistence Context Flushed"
        );

    }

    private PaymentResponse handleExistingPayment(
            PaymentRequest request,
            Billing billing) {

        Payment payment =

                paymentQueryService.findByBilling(

                        billing.getBillingId()

                );

        saveIdempotency(
                request,
                payment
        );

        return buildResponse(
                payment
        );

    }

    private boolean validateAlreadyPaid(
            Billing billing) {

        return paymentRepository

                .existsByBillingBillingId(

                        billing.getBillingId()

                );

    }

    private void audit(Payment payment) {
        auditLogService.paymentCreated(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse findById(UUID paymentId) {

        log.info("==================================");
        log.info("FIND PAYMENT");
        log.info("Payment Id : {}", paymentId);
        log.info("==================================");

        validator.validatePaymentId(paymentId);

        Payment payment =

                paymentQueryService.findById(
                        paymentId
                );

        PaymentResponse response = buildResponse(payment);

        log.info(
                "Payment Found : {}",
                payment.getPaymentId()
        );

        return response;

    }

    /**
     * =========================================================
     * RECEIPT
     * =========================================================
     */

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponse receipt(UUID paymentId) {

        log.info("====================================");
        log.info("GET PAYMENT RECEIPT");
        log.info("PaymentId : {}", paymentId);
        log.info("====================================");

        validator.validatePaymentId(paymentId);

        Payment payment = paymentQueryService.findById(paymentId);

        return receiptService.generate(payment);

    }

    /*
     * =========================================================
     * PRIVATE METHOD
     * =========================================================
     */

    private void validateRequest(PaymentRequest request) {

        if (request == null) {

            throw new InvalidRequestException();

        }

        if (request.getBillingId() == null
                || request.getBillingId().isBlank()) {

            throw new InvalidRequestException();

        }

        if (request.getIdempotencyKey() == null
                || request.getIdempotencyKey().isBlank()) {

            throw new InvalidRequestException();

        }

    }

    @Override
    @Transactional(readOnly = true)
    public Payment findEntity(UUID paymentId){

        return paymentRepository
                .findById(paymentId)
                .orElseThrow(PaymentNotFoundException::new);

    }



}