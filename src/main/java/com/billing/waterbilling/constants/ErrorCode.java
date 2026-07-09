package com.billing.waterbilling.constants;

public final class ErrorCode {

    public static final String SUCCESS = "00";

    public static final String VALIDATION_ERROR = "01";

    public static final String NOT_FOUND = "02";

    public static final String DUPLICATE = "03";

    public static final String BILL_ALREADY_PAID = "04";

    public static final String INVALID_READING = "05";

    public static final String SYSTEM_ERROR = "99";

    private ErrorCode() {
    }
}
