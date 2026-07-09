package com.billing.waterbilling.dto.response;

import com.billing.waterbilling.enums.BillingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private UUID paymentId;

    private String billingId;

    private BigDecimal amount;

    private BigDecimal penalty;

    private BigDecimal total;

    private LocalDateTime paymentDate;

    private BillingStatus billingStatus;

}