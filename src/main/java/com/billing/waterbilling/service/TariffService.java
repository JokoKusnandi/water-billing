package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.response.TariffResponse;
import com.billing.waterbilling.entity.Tariff;

import java.util.List;

public interface TariffService {

//    Tariff findByCode(String tariffCode);

    List<TariffResponse> findAll();

    TariffResponse findByCode(String tariffCode);

    Tariff findEntity(String tariffCode);

}
