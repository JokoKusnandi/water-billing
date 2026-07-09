package com.billing.waterbilling.exception;

public class MeterReadingNotFoundException extends BusinessException {

    public MeterReadingNotFoundException(){

        super(ErrorCode.METER_NOT_FOUND);

    }
}