package com.billing.waterbilling.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {

    private String receiptNumber;

    private UUID paymentId;

    private String paymentNumber;

    private String billingId;

    private String customerId;

    private String customerName;

    private String meterSerialNumber;

    private String tariffName;

    private BigDecimal usage;

    private BigDecimal amount;

    private BigDecimal penalty;

    private BigDecimal total;

    private LocalDateTime paymentDate;

}
