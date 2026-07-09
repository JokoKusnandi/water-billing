package com.billing.waterbilling.controller;

//BillingController
//POST /billings
//
//POST /billings/inquiry
//
//GET /billings/history/{customerId}

import com.billing.waterbilling.dto.request.BillingInquiryRequest;
import com.billing.waterbilling.dto.request.CreateBillingRequest;
import com.billing.waterbilling.dto.response.BillingHistoryResponse;
import com.billing.waterbilling.dto.response.BillingInquiryResponse;
import com.billing.waterbilling.dto.response.BillingResponse;
import com.billing.waterbilling.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public BillingResponse createBilling(
            @Valid @RequestBody CreateBillingRequest request){

        return billingService.createBilling(request);
    }

    @PostMapping("/inquiry")
    public BillingInquiryResponse inquiry(
            @Valid @RequestBody BillingInquiryRequest request){

        return billingService.inquiry(request);
    }

    /**
     * Billing History
     */
    @GetMapping("/history/{customerId}")
    public ResponseEntity<Page<BillingHistoryResponse>> history(
            @PathVariable String customerId,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "dueDate",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        return ResponseEntity.ok(
                billingService.history(customerId, pageable)
        );
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<BillingResponse> findByBillingId(
            @PathVariable String billingId) {

        return ResponseEntity.ok(
                billingService.findByBillingId(billingId)
        );
    }


}
