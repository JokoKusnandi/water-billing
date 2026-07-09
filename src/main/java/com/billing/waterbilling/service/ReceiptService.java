package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.entity.Payment;

public interface ReceiptService {

    ReceiptResponse generate(
            Payment payment
    );

}
