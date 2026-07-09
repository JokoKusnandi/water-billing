package com.billing.waterbilling.mapper;


import com.billing.waterbilling.config.CentralMapperConfig;
import com.billing.waterbilling.dto.response.PaymentResponse;
import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.entity.Payment;
import org.mapstruct.*;

@Mapper(config = CentralMapperConfig.class)
public interface PaymentMapper {

    @Mappings({

            @Mapping(target = "paymentId",
                    source = "paymentId"),

            @Mapping(target = "billingId",
                    source = "billing.billingId"),

            @Mapping(target = "amount",
                    source = "billing.amount"),

            @Mapping(target = "penalty",
                    source = "penalty"),

            @Mapping(target = "total",
                    source = "amountTotal"),

            @Mapping(target = "paymentDate",
                    source = "payDatetime"),

            @Mapping(target = "billingStatus",
                    source = "billing.status")

    })
    PaymentResponse toResponse(Payment payment);

    @Mappings({

            @Mapping(target = "receiptNumber",
                    source = "paymentId"),

            @Mapping(target = "paymentId",
                    source = "paymentId"),

            @Mapping(target = "billingId",
                    source = "billing.billingId"),

            @Mapping(target = "customerId",
                    source = "billing.reading.meter.customer.customerId"),

            @Mapping(target = "customerName",
                    source = "billing.reading.meter.customer.customerName"),

            @Mapping(target = "meterSerialNumber",
                    source = "billing.reading.meter.serialNumber"),

            @Mapping(target = "tariffName",
                    source = "billing.reading.meter.tariff.tariffName"),

            @Mapping(target = "usage",
                    source = "billing.reading.usage"),

            @Mapping(target = "amount",
                    source = "billing.amount"),

            @Mapping(target = "penalty",
                    source = "penalty"),

            @Mapping(target = "total",
                    source = "amountTotal"),

            @Mapping(target = "paymentDate",
                    source = "payDatetime")

    })
    ReceiptResponse toReceipt(Payment payment);

}
