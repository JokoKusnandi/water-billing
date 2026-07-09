package com.billing.waterbilling.exception;

public class CustomerNotFoundException
        extends BusinessException{

    public CustomerNotFoundException(){

        super(ErrorCode.CUSTOMER_NOT_FOUND);

    }

}
