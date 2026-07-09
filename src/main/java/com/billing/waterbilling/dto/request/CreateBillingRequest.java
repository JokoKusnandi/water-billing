package com.billing.waterbilling.dto.request;

import com.billing.waterbilling.validator.ValidPeriod;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBillingRequest {

    @NotBlank
    private String customerId;

    @ValidPeriod
    private String period;

    @NotNull
    @Positive
    @Digits(integer=18,fraction=2)

    private BigDecimal currentReading;

}