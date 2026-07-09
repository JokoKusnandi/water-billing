package com.billing.waterbilling.exception;

public class PaymentAlreadyPaidException
        extends BusinessException {

    public PaymentAlreadyPaidException() {

        super(ErrorCode.PAYMENT_ALREADY_PAID);

    }

}
