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
public class BillingDetailResponse {

    private String billingId;

    private String customerId;

    private String customerName;

    private String email;

    private String phone;

    private String address;

    private String meterSerialNumber;

    private String tariffCode;

    private String tariffName;

    private BigDecimal pricePerM3;

    private BigDecimal penaltyPercent;

    private String period;

    private BigDecimal previousReading;

    private BigDecimal currentReading;

    private BigDecimal usage;

    private BigDecimal amount;

    private LocalDate dueDate;

    private BillingStatus status;

}
