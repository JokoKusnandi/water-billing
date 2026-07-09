package com.billing.waterbilling.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffDetailResponse {

    private String tariffCode;

    private String tariffName;

    private BigDecimal pricePerM3;

    private BigDecimal penaltyPercent;

}
