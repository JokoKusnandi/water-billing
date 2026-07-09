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
public class BillingHistoryResponse {

    private String billingId;

    private String period;

    private String customerId;

    private String customerName;

    private String meterSerialNumber;

    private String tariffCode;

    private String tariffName;

    private BigDecimal usage;

    private BigDecimal amount;

    private LocalDate dueDate;

    private BillingStatus status;

}