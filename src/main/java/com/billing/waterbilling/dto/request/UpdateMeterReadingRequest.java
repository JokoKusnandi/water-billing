package com.billing.waterbilling.dto.request;

import com.billing.waterbilling.validator.ValidPeriod;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMeterReadingRequest {

    @ValidPeriod
    private String period;

    @PositiveOrZero
    private BigDecimal previousReading;

    @PositiveOrZero
    private BigDecimal currentReading;

    @PositiveOrZero
    private BigDecimal usage;

}
