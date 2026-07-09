package com.billing.waterbilling.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tariffs")
public class Tariff extends AuditableEntity {

    @Id
    @Column(name="tariff_code",length=10)
    private String tariffCode;

    @NotBlank
    @Column(name="tariff_name", nullable=false,length=100)
    private String tariffName;

    @Column(name="price_per_m3",nullable=false,precision=18,scale=2)
    private BigDecimal pricePerM3;

    @Column(name="penalty_percents",nullable=false,precision=8,scale=4)
    private BigDecimal penaltyPercent;

    @Builder.Default
    @OneToMany(mappedBy="tariff",fetch=FetchType.LAZY)
    private List<Meter> meters=new ArrayList<>();

}
