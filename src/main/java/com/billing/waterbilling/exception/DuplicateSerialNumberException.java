package com.billing.waterbilling.exception;

public class DuplicateSerialNumberException extends RuntimeException {

    public DuplicateSerialNumberException() {
        super("Meter serial number already exists.");
    }

}