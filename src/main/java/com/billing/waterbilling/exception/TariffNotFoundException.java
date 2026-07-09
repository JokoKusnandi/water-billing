package com.billing.waterbilling.exception;

public class TariffNotFoundException extends BusinessException{

    public TariffNotFoundException(){

        super(ErrorCode.TARIFF_NOT_FOUND);

    }

}