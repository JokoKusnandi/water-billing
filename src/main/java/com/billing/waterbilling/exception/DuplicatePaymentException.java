package com.billing.waterbilling.exception;

public class DuplicatePaymentException
        extends BusinessException {

    public DuplicatePaymentException() {

        super(ErrorCode.DUPLICATE_PAYMENT);

    }

}