package com.billing.waterbilling.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name="meters",
        uniqueConstraints={
                @UniqueConstraint(
                        name="uk_meter_sn",
                        columnNames="serial_number")
        }
)
public class Meter extends AuditableEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long meterId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="customer_id",
            nullable=false)
    private Customer customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="tariff_code",
            nullable=false)
    private Tariff tariff;

    @Column(
            nullable=false,
            unique=true,
            length=100)
    private String serialNumber;

    @Builder.Default
    @OneToMany(
            mappedBy="meter",
            cascade=CascadeType.ALL,
            orphanRemoval=true)
    private List<MeterReading> readings=new ArrayList<>();

}
