package com.billing.waterbilling.exception;


public class ConcurrentPaymentException
        extends BusinessException {

    public ConcurrentPaymentException() {

        super(ErrorCode.PAYMENT_CONCURRENT);

    }

}
