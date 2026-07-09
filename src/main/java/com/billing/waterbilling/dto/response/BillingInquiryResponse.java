package com.billing.waterbilling.dto.response;

import com.billing.waterbilling.enums.BillingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingInquiryResponse {

    private String billingId;

    private String period;

    private BigDecimal usage;

    private BigDecimal amount;

    private LocalDate dueDate;

    private BillingStatus status;

    private CustomerResponse customer;

    private MeterResponse meter;

    private TariffDetailResponse tariff;

}
