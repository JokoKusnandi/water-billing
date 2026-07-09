package com.billing.waterbilling.exception;

public class BillingNotFoundException
        extends BusinessException{

    public BillingNotFoundException(){

        super(ErrorCode.BILLING_NOT_FOUND);

    }



}
