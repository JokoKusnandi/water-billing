package com.billing.waterbilling.entity;

import com.billing.waterbilling.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="billings",
        indexes={
                @Index(
                        name="idx_billing_status",
                        columnList="status"),
                @Index(
                        name="idx_due_date",
                        columnList="due_date")
        }
)
public class Billing extends AuditableEntity {

    @Id
    @Column(length=30)
    private String billingId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="reading_id",
            nullable=false,
            unique=true)
    private MeterReading reading;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal amount;

    @Column(nullable=false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=20)
    private BillingStatus status;

    @OneToOne(
            mappedBy="billing",
            fetch=FetchType.LAZY)
    private Payment payment;

}
