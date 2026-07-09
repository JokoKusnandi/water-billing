package com.billing.waterbilling.mapper;

import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.request.CreateMeterRequest;
import com.billing.waterbilling.dto.request.UpdateMeterRequest;
import com.billing.waterbilling.dto.response.MeterResponse;
import com.billing.waterbilling.entity.Meter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = CentralMapperConfig.class)
public interface MeterMapper {

    /**
     * Create Meter Entity
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({

            @Mapping(target="meterId",ignore=true),

            @Mapping(target="customer",ignore=true),

            @Mapping(target="tariff",ignore=true),

            @Mapping(target="readings",ignore=true),

            @Mapping(target="version",ignore=true),

            @Mapping(target="createdAt",ignore=true),

            @Mapping(target="updatedAt",ignore=true),

            @Mapping(target="serialNumber",
                    source="serialNumber")

    })
    Meter toEntity(CreateMeterRequest request);

    /**
     * Entity -> Response
     */
    @Mappings({

            @Mapping(target = "meterId",
                    source = "meterId"),

            @Mapping(target = "serialNumber",
                    source = "serialNumber"),

            @Mapping(target = "customerId",
                    source = "customer.customerId"),

            @Mapping(target = "customerName",
                    source = "customer.customerName"),

            @Mapping(target = "tariffCode",
                    source = "tariff.tariffCode"),

            @Mapping(target = "tariffName",
                    source = "tariff.tariffName")

    })
    MeterResponse toResponse(Meter meter);

    /**
     * Update Entity
     */
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mappings({

            @Mapping(target="meterId",ignore=true),

            @Mapping(target="customer",ignore=true),

            @Mapping(target="tariff",ignore=true),

            @Mapping(target="readings",ignore=true),

            @Mapping(target="version",ignore=true),

            @Mapping(target="createdAt",ignore=true),

            @Mapping(target="updatedAt",ignore=true),

            @Mapping(target="serialNumber",
                    source="serialNumber")

    })
    void update(@MappingTarget Meter entity,
                UpdateMeterRequest request);

}