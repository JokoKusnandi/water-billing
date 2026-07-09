package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.request.CreateMeterRequest;
import com.billing.waterbilling.dto.response.MeterResponse;
import com.billing.waterbilling.entity.Customer;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;
import com.billing.waterbilling.entity.Tariff;
import com.billing.waterbilling.exception.DuplicateSerialNumberException;
import com.billing.waterbilling.exception.MeterAlreadyExistsException;
import com.billing.waterbilling.exception.MeterNotFoundException;
import com.billing.waterbilling.mapper.MeterMapper;
import com.billing.waterbilling.repository.MeterReadingRepository;
import com.billing.waterbilling.repository.MeterRepository;
import com.billing.waterbilling.service.CustomerService;
import com.billing.waterbilling.service.MeterService;
import com.billing.waterbilling.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MeterServiceImpl implements MeterService {

    private final CustomerService customerService;

    private final TariffService tariffService;

    private final MeterRepository meterRepository;

    private final MeterReadingRepository readingRepository;

    private final MeterMapper meterMapper;

    @Override
    @Transactional
    public MeterResponse create(CreateMeterRequest request) {

        log.info("========== CREATE METER ==========");
        log.info("Customer : {}", request.getCustomerId());
        log.info("Tariff : {}", request.getTariffCode());

        /*
         * STEP 1
         * Validate Customer
         */

        Customer customer = customerService.findEntityById(request.getCustomerId());

        /*
         * STEP 2
         * Validate Tariff
         */

        Tariff tariff =tariffService.findEntity(request.getTariffCode());

        /*STEP 3
        Validation Duplicate Customer*/

        if (meterRepository.existsByCustomerCustomerId(
                request.getCustomerId())) {
            throw new MeterAlreadyExistsException();
        }

        /*
         * STEP 4
         * Validation Duplicate Serial Number
         */

        if (meterRepository.existsBySerialNumber(
                request.getSerialNumber())) {
            throw new DuplicateSerialNumberException();
        }

        /*
         * STEP 5
         * Mapping
         */

        Meter meter = meterMapper.toEntity(request);
        meter.setCustomer(customer);
        meter.setTariff(tariff);

        /*
         * STEP 6
         * Save
         */

        Meter saved = meterRepository.save(meter);

        log.info("Meter Created : {}",saved.getMeterId());

        /*
         * STEP 5
         * Response
         */

        return meterMapper.toResponse(saved);

    }

    @Override
    @Transactional(readOnly = true)
    public Meter findCustomerMeterEntity(String customerId) {
        return meterRepository
                .findByCustomerCustomerId(customerId)
                .orElseThrow(MeterNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public MeterResponse findCustomerMeter(String customerId) {
        return meterMapper.toResponse(
                findCustomerMeterEntity(customerId)
        );
    }

    @Override
    public MeterReading latestReading(Long meterId) {
        return readingRepository
                .findTopByMeterMeterIdOrderByPeriodDesc(meterId)
                .orElse(null);
    }

    @Override
    public Meter findEntity(Long meterId) {
        log.debug("Find meter entity : {}", meterId);
        return meterRepository
                .findById(meterId)
                .orElseThrow(MeterNotFoundException::new);
    }

    @Override
    public MeterResponse findById(Long meterId) {
        return meterMapper.toResponse(
                findEntity(meterId)
        );
    }

}
