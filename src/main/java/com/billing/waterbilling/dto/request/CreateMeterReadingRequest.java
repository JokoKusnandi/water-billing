package com.billing.waterbilling.dto.request;

import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.validator.ValidPeriod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMeterReadingRequest {

    @NotNull
    private Long meterId;

    @ValidPeriod
    private String period;

    @PositiveOrZero
    private BigDecimal previousReading;

    @PositiveOrZero
    private BigDecimal currentReading;

    @PositiveOrZero
    private BigDecimal usage;

}