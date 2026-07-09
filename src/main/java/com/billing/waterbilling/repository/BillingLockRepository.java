package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Billing;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BillingLockRepository extends JpaRepository<Billing,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""

            select b

            from Billing b

            join fetch b.reading r

            join fetch r.meter m

            join fetch m.customer

            join fetch m.tariff

            where b.billingId=:billingId

            """)

    Optional<Billing>
    lockBilling(
            @Param("billingId")
            String billingId

    );

}
