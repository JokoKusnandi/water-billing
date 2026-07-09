package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.request.CreateMeterReadingRequest;
import com.billing.waterbilling.dto.request.UpdateMeterReadingRequest;
import com.billing.waterbilling.dto.response.MeterReadingResponse;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;
import com.billing.waterbilling.exception.MeterReadingNotFoundException;
import com.billing.waterbilling.mapper.MeterReadingMapper;
import com.billing.waterbilling.repository.MeterReadingRepository;
import com.billing.waterbilling.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MeterReadingServiceImpl
        implements MeterReadingService {

    private final MeterReadingRepository repository;

    private final MeterReadingMapper mapper;

    @Override
    public MeterReading create(
            Meter meter,
            CreateMeterReadingRequest request) {

        MeterReading entity = mapper.toEntity(request);

        entity.setMeter(meter);

        return repository.save(entity);
    }

    @Transactional
    public MeterReadingResponse update(
            Long readingId,
            UpdateMeterReadingRequest request){

        MeterReading entity = repository.findById(readingId)
                .orElseThrow(MeterReadingNotFoundException::new);

        mapper.update(entity, request);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MeterReading findEntity(Long readingId) {

        return repository.findById(readingId)
                .orElseThrow(MeterReadingNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public MeterReadingResponse findById(Long readingId) {

        return mapper.toResponse(
                findEntity(readingId)
        );
    }

}