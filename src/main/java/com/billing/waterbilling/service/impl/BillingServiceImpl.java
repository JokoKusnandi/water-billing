package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.constants.IdGenerator;
import com.billing.waterbilling.dto.request.BillingInquiryRequest;
import com.billing.waterbilling.dto.request.CreateBillingRequest;
import com.billing.waterbilling.dto.request.CreateMeterReadingRequest;
import com.billing.waterbilling.dto.response.BillingHistoryResponse;
import com.billing.waterbilling.dto.response.BillingInquiryResponse;
import com.billing.waterbilling.dto.response.BillingResponse;
import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.entity.Customer;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;
import com.billing.waterbilling.enums.BillingStatus;
import com.billing.waterbilling.exception.BillingAlreadyPaidException;
import com.billing.waterbilling.exception.BillingNotFoundException;
import com.billing.waterbilling.mapper.BillingMapper;
import com.billing.waterbilling.repository.BillingRepository;
import com.billing.waterbilling.repository.MeterReadingRepository;
import com.billing.waterbilling.service.BillingService;
import com.billing.waterbilling.service.CustomerService;
import com.billing.waterbilling.service.MeterReadingService;
import com.billing.waterbilling.service.MeterService;
import com.billing.waterbilling.util.BillingCalculator;
import com.billing.waterbilling.util.DueDateUtil;
import com.billing.waterbilling.validator.BusinessValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(
        rollbackFor = Exception.class
)
public class BillingServiceImpl implements BillingService {

    private final CustomerService customerService;

    private final MeterService meterService;

    private final MeterReadingService meterReadingService;

    private final MeterReadingRepository readingRepository;

    private final BillingRepository billingRepository;

    private final BillingCalculator calculator;

    private final BusinessValidator validator;

    private final DueDateUtil dueDateUtil;

    private final BillingMapper billingMapper;


    @Override
    public BillingResponse createBilling(CreateBillingRequest request) {

        log.info("========== CREATE BILLING ==========");

        log.info("Customer : {}", request.getCustomerId());

        log.info("Period : {}", request.getPeriod());

        log.info("Current Reading : {}",
                request.getCurrentReading());

        /*
         *
         * STEP 1
         * Customer Validation
         *
         */

        Customer customer = customerService.findEntityById(request.getCustomerId());

        /*
         *
         * STEP 2
         * Meter
         *
         */

        Meter meter = meterService.findCustomerMeterEntity(
                        request.getCustomerId());

        /*
         *
         * STEP 3
         * Duplicate Period
         *
         */

        validator.validateDuplicateBilling(
                readingRepository.existsByMeterMeterIdAndPeriod(
                        meter.getMeterId(),
                        request.getPeriod()
                )
        );

        /*
         *
         * STEP 4
         * Previous Reading
         *
         */

        MeterReading latestReading =
                meterService.latestReading(
                        meter.getMeterId()
                );

        BigDecimal previousReading;

        if (latestReading == null) {
            previousReading = BigDecimal.ZERO;
        } else {
            previousReading = latestReading.getCurrentReading();
        }

        /*
         *
         * STEP 5
         * Validation
         *
         */

        validator.validateReading(
                request.getCurrentReading(),
                previousReading
        );

        /*
         *
         * STEP 6
         * Usage
         *
         */

        BigDecimal usage =
                calculator.usage(
                        previousReading,
                        request.getCurrentReading()
                );

        /*
         *
         * STEP 7
         * Amount
         *
         */

        BigDecimal amount =
                calculator.amount(
                        usage,
                        meter.getTariff()
                                .getPricePerM3()
                );

        /*
         *
         * STEP 8
         * Create Meter Reading
         *
         */

        CreateMeterReadingRequest readingRequest =
                CreateMeterReadingRequest.builder()
                        .meterId(meter.getMeterId())
                        .period(request.getPeriod())
                        .previousReading(previousReading)
                        .currentReading(request.getCurrentReading())
                        .usage(usage)
                        .build();

        MeterReading reading =
                meterReadingService.create(meter, readingRequest);

        /*
         *
         * STEP 9
         * Billing
         *
         */

        Billing billing =
                Billing.builder()
                        .billingId(
                                IdGenerator.generateBillingId())
                        .reading(reading)
                        .amount(amount)
                        .status(
                                BillingStatus.UNPAID)
                        .dueDate(
                                dueDateUtil.dueDate(
                                        request.getPeriod()
                                )
                        )
                        .build();

        billing = billingRepository.save(billing);

        log.info("Billing Created {}",
                billing.getBillingId());

        return billingMapper.toResponse(
                billing);

    }

//    @Override
//    @Transactional(readOnly = true)
    /*public BillingDetailResponse inquiry(BillingInquiryRequest request) {

        log.info("========== BILL INQUIRY ==========");

        log.info("Customer : {}", request.getCustomerId());

        log.info("Period : {}", request.getPeriod());

        *//*
         * STEP 1
         * Validate Customer
         *//*

        customerService.findById(
                request.getCustomerId());

        *//*
         * STEP 2
         * Find Billing
         *//*

        Billing billing = billingRepository

                .findByReadingMeterCustomerCustomerIdAndReadingPeriod(

                        request.getCustomerId(),

                        request.getPeriod()

                )

                .orElseThrow(BillingNotFoundException::new);

        *//*
         * STEP 3
         * Status Validation
         *//*

        if (billing.getStatus() == BillingStatus.PAID) {

            throw new BillingAlreadyPaidException();

        }

        *//*
         * STEP 4
         * Logging
         *//*

        log.info("Billing : {}", billing.getBillingId());

        log.info("Amount : {}", billing.getAmount());

        log.info("Usage : {}",
                billing.getReading().getUsage());

        log.info("Due Date : {}",
                billing.getDueDate());

        *//*
         * STEP 5
         * Mapping
         *//*

        BillingDetailResponse response =

                billingMapper.toDetailResponse(
                        billing
                );

        log.info("Inquiry Success");

        return response;

    }
*/
    @Override
    @Transactional(readOnly = true)
    public BillingInquiryResponse inquiry(
            BillingInquiryRequest request) {

        customerService.findById(request.getCustomerId());

        Billing billing = billingRepository
                .findByReadingMeterCustomerCustomerIdAndReadingPeriod(
                        request.getCustomerId(),
                        request.getPeriod())
                .orElseThrow(BillingNotFoundException::new);

        if (billing.getStatus() == BillingStatus.PAID) {
            throw new BillingAlreadyPaidException();
        }

        return billingMapper.toInquiryResponse(billing);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BillingHistoryResponse> history(
            String customerId,
            Pageable pageable) {

        log.info("========== BILLING HISTORY ==========");

        log.info("Customer : {}", customerId);

        /*
         * STEP 1
         * Validate Customer
         */

        customerService.findById(customerId);

        /*
         * STEP 2
         * Load Billing History
         */

        Page<Billing> page =

                billingRepository
                        .findByReadingMeterCustomerCustomerIdOrderByReadingPeriodDesc(
                                customerId,
                                pageable
                        );

        /*
         * STEP 3
         * Mapping
         */

        Page<BillingHistoryResponse> response =

                page.map(
                        billingMapper::toHistory
                );

        /*
         * STEP 4
         * Logging
         */

        log.info("Total Record : {}",
                response.getTotalElements());

        log.info("Page : {}",
                response.getNumber());

        log.info("Size : {}",
                response.getSize());

        return response;

    }

    @Override
    @Transactional(readOnly = true)
    public BillingResponse findByBillingId(String billingId) {

        log.info("========== FIND BILLING ==========");
        log.info("Billing ID : {}", billingId);

        Billing billing = validator.validateBilling(
                billingRepository.findByBillingId(billingId),
                billingId
        );

        return billingMapper.toResponse(billing);
    }

    @Override
    @Transactional(readOnly = true)
    public Billing findEntity(String billingId) {

        return validator.validateBilling(
                billingRepository.findByBillingId(billingId),
                billingId
        );

    }




}
