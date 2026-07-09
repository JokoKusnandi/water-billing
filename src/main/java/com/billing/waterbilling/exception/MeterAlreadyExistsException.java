package com.billing.waterbilling.exception;

public class MeterAlreadyExistsException extends RuntimeException {

    public MeterAlreadyExistsException() {
        super("Customer already has a meter.");
    }

}
