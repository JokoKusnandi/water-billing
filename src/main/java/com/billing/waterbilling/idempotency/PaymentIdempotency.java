package com.billing.waterbilling.idempotency;

import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "payment_idempotencies",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_idempotency_key",
                        columnNames = "idempotency_key"
                )
        }
)
public class PaymentIdempotency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "idempotency_key",
            nullable = false,
            length = 120
    )
    private String idempotencyKey;

    /**
     * FK -> billings.billing_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "billing_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_payment_idempotency_billing"
            )
    )
    private Billing billing;

    /**
     * FK -> payments.payment_id
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_id",
            foreignKey = @ForeignKey(
                    name = "fk_payment_idempotency_payment"
            )
    )
    private Payment payment;

    @Column(length = 255)
    private String endpoint;

    @Lob
    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

}