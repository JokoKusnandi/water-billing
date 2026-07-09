package com.billing.waterbilling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payments")
public class Payment extends AuditableEntity {

    @Id
    private UUID paymentId;

    @Column(nullable = false, unique = true)
    private String paymentNumber;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="billing_id",
            nullable=false,
            unique=true)
    private Billing billing;

    @Column(nullable=false)
    private LocalDateTime payDatetime;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal penalty;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal amountTotal;

}
