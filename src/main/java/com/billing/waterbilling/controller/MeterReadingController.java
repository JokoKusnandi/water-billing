package com.billing.waterbilling.controller;

import com.billing.waterbilling.dto.request.CreateMeterReadingRequest;
import com.billing.waterbilling.dto.request.UpdateMeterReadingRequest;
import com.billing.waterbilling.dto.response.MeterReadingResponse;
import com.billing.waterbilling.entity.Meter;
import com.billing.waterbilling.entity.MeterReading;
import com.billing.waterbilling.mapper.MeterReadingMapper;
import com.billing.waterbilling.service.MeterReadingService;
import com.billing.waterbilling.service.MeterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/readings")
@RequiredArgsConstructor
public class MeterReadingController {

    private final MeterService meterService;
    private final MeterReadingService meterReadingService;
    private final MeterReadingMapper mapper;


    @PostMapping
    public ResponseEntity<MeterReadingResponse> create(
            @Valid @RequestBody CreateMeterReadingRequest request) {

        Meter meter = meterService.findEntity(request.getMeterId());

        MeterReading reading =
                meterReadingService.create(meter, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(reading));
    }

    @PutMapping("/{readingId}")
    public MeterReadingResponse update(
            @PathVariable Long readingId,
            @Valid @RequestBody UpdateMeterReadingRequest request){

        return meterReadingService.update(readingId,request);
    }

    /*@GetMapping("/{readingId}")
    public MeterReadingResponse findById(
            @PathVariable Long readingId){

        return service.findById(readingId);
    }*/

    /*@GetMapping
    public List<MeterReadingResponse> findAll(){

        return service.findAll();
    }*/

}
