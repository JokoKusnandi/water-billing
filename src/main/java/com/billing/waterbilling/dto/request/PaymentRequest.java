package com.billing.waterbilling.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotBlank
    private String billingId;

    @NotBlank
    private String idempotencyKey;

}
