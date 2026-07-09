package com.billing.waterbilling.dto.response;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeterReadingResponse {

    private Long readingId;

    private Long meterId;

    private String serialNumber;

    private String customerId;

    private String period;

    private BigDecimal previousReading;

    private BigDecimal currentReading;

    private BigDecimal usage;

}
