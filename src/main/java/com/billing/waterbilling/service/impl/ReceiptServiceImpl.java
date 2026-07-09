package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.entity.Payment;
import com.billing.waterbilling.mapper.ReceiptMapper;
import com.billing.waterbilling.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptMapper receiptMapper;

    @Override
    public ReceiptResponse generate(Payment payment) {

        return receiptMapper.toResponse(payment);

    }

}