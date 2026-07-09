package com.billing.waterbilling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS("00","Success"),

    VALIDATION_ERROR("01","Validation Error"),

    CUSTOMER_NOT_FOUND("02","Customer Not Found"),

    DUPLICATE_CUSTOMER("03","Customer Already Exists"),

    DUPLICATE_EMAIL("04","Customer Already Exists"),

    TARIFF_NOT_FOUND("05","Tariff Not Found"),

    METER_NOT_FOUND("07","Meter Not Found"),

    BILLING_NOT_FOUND("08","Billing Not Found"),

    DUPLICATE_BILLING("09","Billing Already Exists"),

    BILL_ALREADY_PAID("10","Billing Already Paid"),

    INVALID_READING("11","Invalid Meter Reading"),

    INVALID_REQUEST("12","Invalid request"),

    IDEMPOTENCY_NOT_FOUND("13","Idempotency Not Found"),

    IDEMPOTENCY_ERROR("14","Duplicate Request"),

    DUPLICATE_IDEMPOTENCY_KEY("PAY-15","Duplicate idempotency key."),

    PAYMENT_NOT_FOUND("16","Payment Not Found"),

    PAYMENT_CONCURRENT(
            "PAY409",
            "Payment is being processed by another transaction."
    ),

    PAYMENT_ALREADY_PAID(
            "PAY410",
            "Billing has already been paid."
    ),

    DUPLICATE_PAYMENT(
            "PAY411",
            "Duplicate payment detected."
    ),

    SYSTEM_ERROR("99","Internal Server Error");

    private final String code;

    private final String message;

}
