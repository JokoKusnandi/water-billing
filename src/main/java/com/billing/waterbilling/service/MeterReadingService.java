package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.request.CreateMeterReadingRequest;
import com.billing.waterbilling.dto.request.UpdateMeterReadingRequest;
import com.billing.waterbilling.dto.response.MeterReadingResponse;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;


public interface MeterReadingService {

    MeterReading create(
            Meter meter,
            CreateMeterReadingRequest request);

    MeterReadingResponse findById(Long readingId);

    MeterReadingResponse update(
            Long readingId,
            UpdateMeterReadingRequest request);

    MeterReading findEntity(Long readingId);

}
