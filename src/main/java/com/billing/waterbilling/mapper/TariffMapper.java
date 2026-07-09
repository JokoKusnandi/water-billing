package com.billing.waterbilling.mapper;

import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.response.TariffDetailResponse;
import com.billing.waterbilling.dto.response.TariffResponse;
import com.billing.waterbilling.entity.Tariff;
import org.mapstruct.Mapper;

@Mapper(config = CentralMapperConfig.class)
public interface TariffMapper {

    TariffResponse toResponse(Tariff entity);

    TariffDetailResponse toDetailResponse(Tariff entity);

}
