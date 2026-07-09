package com.billing.waterbilling.exception;

public class DuplicateCustomerException extends BusinessException{

    public DuplicateCustomerException(){

        super(ErrorCode.DUPLICATE_CUSTOMER);

    }

}