package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.response.TariffResponse;
import com.billing.waterbilling.entity.Tariff;
import com.billing.waterbilling.exception.TariffNotFoundException;
import com.billing.waterbilling.mapper.TariffMapper;
import com.billing.waterbilling.repository.TariffRepository;
import com.billing.waterbilling.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TariffServiceImpl implements TariffService {

    private final TariffRepository repository;
    private final TariffMapper mapper;

    @Override
    public TariffResponse findByCode(String tariffCode) {

        return mapper.toResponse(
                findEntity(tariffCode)
        );

    }

    @Override
    public Tariff findEntity(String tariffCode) {

        log.debug("Find tariff {}", tariffCode);

        return repository.findById(tariffCode)
                .orElseThrow(TariffNotFoundException::new);

    }

    @Override
    public List<TariffResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

}