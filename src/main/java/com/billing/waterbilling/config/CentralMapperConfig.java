package com.billing.waterbilling.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface CentralMapperConfig {
}
