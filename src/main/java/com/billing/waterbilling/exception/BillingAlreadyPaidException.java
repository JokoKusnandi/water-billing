package com.billing.waterbilling.exception;

public class BillingAlreadyPaidException
        extends BusinessException{

    public BillingAlreadyPaidException(){

        super(ErrorCode.BILL_ALREADY_PAID);

    }

}
