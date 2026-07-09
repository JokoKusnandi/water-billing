package com.billing.waterbilling.exception;

public class PaymentNotFoundException extends BusinessException{

    public PaymentNotFoundException(){

        super(ErrorCode.PAYMENT_NOT_FOUND);

    }

}
