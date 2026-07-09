package com.billing.waterbilling.mapper;

import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.response.BillingDetailResponse;
import com.billing.waterbilling.dto.response.BillingHistoryResponse;
import com.billing.waterbilling.dto.response.BillingInquiryResponse;
import com.billing.waterbilling.dto.response.BillingResponse;
import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.entity.MeterReading;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                CustomerMapper.class,
                MeterMapper.class,
                TariffMapper.class
        }
)
public interface BillingMapper {

    /**
     * Billing Inquiry Response
     */
    @Mapping(target = "billingId", source = "billingId")
    @Mapping(target = "period", source = "reading.period")
    @Mapping(target = "usage", source = "reading.usage")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "status", source = "status")

    @Mapping(target = "customer",
            source = "reading.meter.customer")

    @Mapping(target = "meter",
            source = "reading.meter")

    @Mapping(target = "tariff",
            source = "reading.meter.tariff")
    BillingInquiryResponse toInquiryResponse(Billing billing);

    @Mappings({

            @Mapping(target = "billingId",
                    source = "billingId"),

            @Mapping(target = "customerId",
                    source = "reading.meter.customer.customerId"),

            @Mapping(target = "customerName",
                    source = "reading.meter.customer.customerName"),

            @Mapping(target = "address",
                    source = "reading.meter.customer.address"),

            @Mapping(target = "meterSerialNumber",
                    source = "reading.meter.serialNumber"),

            @Mapping(target = "tariffCode",
                    source = "reading.meter.tariff.tariffCode"),

            @Mapping(target = "tariffName",
                    source = "reading.meter.tariff.tariffName"),

            @Mapping(target = "pricePerM3",
                    source = "reading.meter.tariff.pricePerM3"),

            @Mapping(target = "period",
                    source = "reading.period"),

            @Mapping(target = "usage",
                    source = "reading.usage"),

            @Mapping(target = "amount",
                    source = "amount"),

            @Mapping(target = "dueDate",
                    source = "dueDate"),

            @Mapping(target = "status",
                    source = "status")

    })
    BillingResponse toResponse(Billing billing);

    /**
     * Billing Detail
     */
    @Mappings({

            @Mapping(target = "customerId",
                    source = "reading.meter.customer.customerId"),

            @Mapping(target = "customerName",
                    source = "reading.meter.customer.customerName"),

            @Mapping(target = "email",
                    source = "reading.meter.customer.email"),

            @Mapping(target = "phone",
                    source = "reading.meter.customer.phone"),

            @Mapping(target = "address",
                    source = "reading.meter.customer.address"),

            @Mapping(target = "meterSerialNumber",
                    source = "reading.meter.serialNumber"),

            @Mapping(target = "tariffCode",
                    source = "reading.meter.tariff.tariffCode"),

            @Mapping(target = "tariffName",
                    source = "reading.meter.tariff.tariffName"),

            @Mapping(target = "pricePerM3",
                    source = "reading.meter.tariff.pricePerM3"),

            @Mapping(target = "penaltyPercent",
                    source = "reading.meter.tariff.penaltyPercent"),

            @Mapping(target = "period",
                    source = "reading.period"),

            @Mapping(target = "previousReading",
                    source = "reading.previousReading"),

            @Mapping(target = "currentReading",
                    source = "reading.currentReading"),

            @Mapping(target = "usage",
                    source = "reading.usage")

    })
    BillingDetailResponse toDetailResponse(Billing billing);

    /**
     * Billing History
     */
    @Mappings({

            @Mapping(target = "billingId",
                    source = "billingId"),

            @Mapping(target = "period",
                    source = "reading.period"),

            @Mapping(target = "customerId",
                    source = "reading.meter.customer.customerId"),

            @Mapping(target = "customerName",
                    source = "reading.meter.customer.customerName"),

            @Mapping(target = "meterSerialNumber",
                    source = "reading.meter.serialNumber"),

            @Mapping(target = "tariffCode",
                    source = "reading.meter.tariff.tariffCode"),

            @Mapping(target = "tariffName",
                    source = "reading.meter.tariff.tariffName"),

            @Mapping(target = "usage",
                    source = "reading.usage"),

            @Mapping(target = "amount",
                    source = "amount"),

            @Mapping(target = "dueDate",
                    source = "dueDate"),

            @Mapping(target = "status",
                    source = "status")

    })
    BillingHistoryResponse toHistory(Billing billing);

    /**
     * Create Meter Reading
     */
    @BeanMapping(ignoreByDefault = true)
    @Mappings({

            @Mapping(target = "meter",
                    ignore = true),

            @Mapping(target = "period",
                    source = "period"),

            @Mapping(target = "previousReading",
                    source = "previousReading"),

            @Mapping(target = "currentReading",
                    source = "currentReading"),

            @Mapping(target = "usage",
                    source = "usage")

    })
    MeterReading toMeterReading(String period,
                                java.math.BigDecimal previousReading,
                                java.math.BigDecimal currentReading,
                                java.math.BigDecimal usage);

    /**
     * After Mapping
     */
    @AfterMapping
    default void afterBilling(@MappingTarget BillingResponse response,
                              Billing billing) {

        if (billing == null) {
            return;
        }

        if (billing.getReading() == null) {
            return;
        }

    }

}
