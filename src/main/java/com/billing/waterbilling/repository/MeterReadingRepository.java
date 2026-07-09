package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.MeterReading;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends
        JpaRepository<MeterReading,Long>,
        JpaSpecificationExecutor<MeterReading> {

    Optional<MeterReading>

    findTopByMeterMeterIdOrderByPeriodDesc(Long meterId);

    Optional<MeterReading>

    findByMeterMeterIdAndPeriod(Long meterId,
                                String period);

    boolean existsByMeterMeterIdAndPeriod(Long meterId,
                                          String period);

    List<MeterReading>

    findByMeterMeterIdOrderByPeriodDesc(Long meterId);

}
