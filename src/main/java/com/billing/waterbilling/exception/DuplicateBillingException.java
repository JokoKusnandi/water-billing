package com.billing.waterbilling.exception;

public class DuplicateBillingException
        extends BusinessException{

    public DuplicateBillingException(){

        super(ErrorCode.DUPLICATE_BILLING);

    }

}