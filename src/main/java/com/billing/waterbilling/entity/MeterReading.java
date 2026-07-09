package com.billing.waterbilling.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="meter_readings",
        uniqueConstraints={
                @UniqueConstraint(
                        name="uk_meter_period",
                        columnNames={
                                "meter_id",
                                "period"
                        })
        }
)
public class MeterReading extends AuditableEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long readingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "meter_id",
            nullable = false
    )
    private Meter meter;

    @Column(nullable=false,length=6)
    private String period;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal previousReading;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal currentReading;

    @Column(nullable=false,precision=18,scale=2)
    private BigDecimal usage;

    @OneToOne(
            mappedBy="reading",
            fetch=FetchType.LAZY)
    private Billing billing;

}
