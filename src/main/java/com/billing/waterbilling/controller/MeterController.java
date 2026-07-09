package com.billing.waterbilling.controller;

import com.billing.waterbilling.dto.request.CreateMeterRequest;
import com.billing.waterbilling.dto.response.MeterResponse;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;
import com.billing.waterbilling.service.MeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/meters")
@RequiredArgsConstructor
public class MeterController {

    private final MeterService meterService;

    @PostMapping
    public ResponseEntity<MeterResponse> create(
            @Valid @RequestBody CreateMeterRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(meterService.create(request));
    }

    /*@PutMapping("/{meterId}")
    public MeterResponse update(
            @PathVariable Long meterId,
            @Valid @RequestBody UpdateMeterRequest request){

        return meterService.update(meterId,request);
    }*/

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<MeterResponse> findCustomerMeterEntity(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                meterService.findCustomerMeter(customerId)
        );
    }


    @GetMapping("/mreading/{meterId}")
    public MeterReading latestReading(
            @PathVariable Long meterId){

        return meterService.latestReading(meterId);
    }

    @GetMapping("/{meterId}")
    public MeterResponse findById(
            @PathVariable Long meterId){

        return meterService.findById(meterId);
    }

    @GetMapping("/meter/{meterId}")
    public Meter findEntity(
            @PathVariable Long meterId){

        return meterService.findEntity(meterId);
    }

    /*@GetMapping
    public List<MeterResponse> findAll(){

        return meterService.findAll();
    }*/

   /* @DeleteMapping("/{meterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long meterId){

        meterService.delete(meterId);
    }*/

}
