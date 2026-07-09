package com.billing.waterbilling.exception;

public class IdempotencyNotFoundException extends BusinessException{

    public IdempotencyNotFoundException(){

        super(ErrorCode.IDEMPOTENCY_NOT_FOUND);

    }

}