package com.billing.waterbilling.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillingResult {

    private Long readingId;

    private String billingId;

}
