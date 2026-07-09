package com.billing.waterbilling.dto.request;

import com.billing.waterbilling.validator.ValidPeriod;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingInquiryRequest {

    @NotBlank
    private String customerId;

    @ValidPeriod
    private String period;

}
