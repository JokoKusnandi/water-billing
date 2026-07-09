package com.billing.waterbilling.service;

import com.billing.waterbilling.dto.request.BillingInquiryRequest;
import com.billing.waterbilling.dto.request.CreateBillingRequest;
import com.billing.waterbilling.dto.response.BillingDetailResponse;
import com.billing.waterbilling.dto.response.BillingHistoryResponse;
import com.billing.waterbilling.dto.response.BillingInquiryResponse;
import com.billing.waterbilling.dto.response.BillingResponse;
import com.billing.waterbilling.entity.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillingService {

    BillingResponse createBilling(CreateBillingRequest request);

    /**
     * Bill Inquiry
     */
    BillingInquiryResponse inquiry(BillingInquiryRequest request);

    /**
     * Billing History
     */
    Page<BillingHistoryResponse> history(String customerId, Pageable pageable);

    /**
     * Find Billing By Id
     */
    BillingResponse findByBillingId(String billingId);


    Billing findEntity(String billingId);

}
