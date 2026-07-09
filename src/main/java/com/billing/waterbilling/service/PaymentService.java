package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.request.PaymentRequest;
import com.billing.waterbilling.dto.response.PaymentResponse;
import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.entity.Payment;

import java.util.UUID;


public interface PaymentService {

    PaymentResponse pay(PaymentRequest request);

    PaymentResponse findById(UUID paymentId);

    ReceiptResponse receipt(UUID  paymentId);

    Payment findEntity(UUID paymentId);

}
