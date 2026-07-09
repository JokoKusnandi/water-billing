package com.billing.waterbilling.exception;

public class DuplicateIdempotencyKeyException
        extends BusinessException {

    public DuplicateIdempotencyKeyException() {

        super(ErrorCode.DUPLICATE_IDEMPOTENCY_KEY);

    }

}
