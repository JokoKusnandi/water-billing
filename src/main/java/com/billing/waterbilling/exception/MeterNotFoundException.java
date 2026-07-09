package com.billing.waterbilling.exception;

public class MeterNotFoundException extends BusinessException {

    public MeterNotFoundException(){

        super(ErrorCode.METER_NOT_FOUND);

    }
}