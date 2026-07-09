package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.request.CreateMeterRequest;
import com.billing.waterbilling.dto.response.MeterResponse;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;

public interface MeterService {

    MeterResponse create(CreateMeterRequest request);

    MeterResponse findCustomerMeter(String customerId);

    Meter findCustomerMeterEntity(String customerId);

    MeterReading latestReading(Long meterId);

    MeterResponse findById(Long meterId);

    Meter findEntity(Long meterId);

}
