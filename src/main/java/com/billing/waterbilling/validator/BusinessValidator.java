package com.billing.waterbilling.validator;

import com.billing.waterbilling.entity.Billing;
import com.billing.waterbilling.enums.BillingStatus;
import com.billing.waterbilling.exception.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class BusinessValidator {

    public void validateReading(BigDecimal current,
                                BigDecimal previous){

        if(current.compareTo(previous)<0){

            throw new InvalidMeterReadingException();

        }

    }

    public void validateDuplicateBilling(boolean exists){

        if(exists){

            throw new DuplicateBillingException();

        }

    }

    public Billing validateBilling(
            Optional<Billing> billing,
            String billingId){

        return billing.orElseThrow(
                BillingNotFoundException::new
        );

    }

    public void validateBillingStatus(
            BillingStatus status){

        if(status == BillingStatus.PAID){

            throw new BillingAlreadyPaidException();

        }

    }

    public void validatePaymentId(
            UUID paymentId){
        if(paymentId == null){
            throw new InvalidRequestException();
        }

    }

}
