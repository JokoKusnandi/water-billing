package com.billing.waterbilling.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterResponse {

    private Long meterId;

    private String customerId;

    private String customerName;

    private String tariffCode;

    private String tariffName;

    private String serialNumber;

}
