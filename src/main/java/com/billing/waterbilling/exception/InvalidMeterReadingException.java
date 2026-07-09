package com.billing.waterbilling.exception;

public class InvalidMeterReadingException extends BusinessException{
    public InvalidMeterReadingException(){
        super(ErrorCode.INVALID_READING);

    }

}
