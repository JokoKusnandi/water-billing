package com.billing.waterbilling.controller;

import com.billing.waterbilling.dto.request.PaymentRequest;
import com.billing.waterbilling.dto.response.PaymentResponse;
import com.billing.waterbilling.dto.response.ReceiptResponse;
import com.billing.waterbilling.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * =========================================================
     * PAYMENT
     * POST /api/v1/payments
     * =========================================================
     */
    @PostMapping
    public ResponseEntity<PaymentResponse> pay(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.pay(request));
    }

    /**
     * =========================================================
     * FIND PAYMENT
     * GET /api/v1/payments/{paymentId}
     * =========================================================
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> findById(
            @PathVariable UUID paymentId) {

        return ResponseEntity.ok(
                paymentService.findById(paymentId)
        );
    }

    /**
     * =========================================================
     * PAYMENT RECEIPT
     * GET /api/v1/payments/{paymentId}/receipt
     * =========================================================
     */
    @GetMapping("/{paymentId}/receipt")
    public ResponseEntity<ReceiptResponse> receipt(
            @PathVariable UUID paymentId) {

        return ResponseEntity.ok(
                paymentService.receipt(paymentId)
        );
    }

}
