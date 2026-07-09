package com.billing.waterbilling.mapper;

import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.request.CreateMeterReadingRequest;
import com.billing.waterbilling.dto.request.UpdateMeterReadingRequest;
import com.billing.waterbilling.dto.response.MeterReadingResponse;
import com.billing.waterbilling.entity.MeterReading;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface MeterReadingMapper {

    /**
     * Create Entity
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({

            @Mapping(target = "readingId", ignore = true),

            @Mapping(target = "meter", ignore = true),

            @Mapping(target = "billing", ignore = true),

            @Mapping(target = "version", ignore = true),

            @Mapping(target = "createdAt", ignore = true),

            @Mapping(target = "updatedAt", ignore = true),

            @Mapping(target = "period", source = "period"),

            @Mapping(target = "previousReading", source = "previousReading"),

            @Mapping(target = "currentReading", source = "currentReading"),

            @Mapping(target = "usage", source = "usage")

    })
    MeterReading toEntity(CreateMeterReadingRequest request);

    /**
     * Entity -> Response
     */
    @Mappings({

            @Mapping(target = "readingId", source = "readingId"),

            @Mapping(target = "meterId", source = "meter.meterId"),

            @Mapping(target = "serialNumber", source = "meter.serialNumber"),

            @Mapping(target = "customerId", source = "meter.customer.customerId"),

            @Mapping(target = "period", source = "period"),

            @Mapping(target = "previousReading", source = "previousReading"),

            @Mapping(target = "currentReading", source = "currentReading"),

            @Mapping(target = "usage", source = "usage")

    })
    MeterReadingResponse toResponse(MeterReading entity);

    /**
     * Update Entity
     */
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mappings({

            @Mapping(target = "readingId", ignore = true),

            @Mapping(target = "meter", ignore = true),

            @Mapping(target = "billing", ignore = true),

            @Mapping(target = "createdAt", ignore = true),

            @Mapping(target = "updatedAt", ignore = true),

            @Mapping(target = "version", ignore = true)


    })
    void update(@MappingTarget MeterReading entity,
                UpdateMeterReadingRequest request);

}