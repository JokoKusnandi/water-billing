package com.billing.waterbilling.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "customers",
        indexes = {
                @Index(name = "idx_customer_name", columnList = "customer_name"),
                @Index(name = "idx_customer_email", columnList = "email")
        }
)
public class Customer extends AuditableEntity {

    @Id
    @Column(name = "customer_id", length = 20)
    private String customerId;

    @NotBlank
    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @Email
    @Column(length = 120)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Builder.Default
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Meter> meters = new ArrayList<>();

    public void addMeter(Meter meter){
        meters.add(meter);
        meter.setCustomer(this);
    }

}