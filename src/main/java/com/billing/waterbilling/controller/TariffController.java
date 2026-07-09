package com.billing.waterbilling.controller;

import com.billing.waterbilling.dto.response.TariffResponse;
import com.billing.waterbilling.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final TariffService tariffService;

    /*@PostMapping
    public TariffResponse create(
            @Valid @RequestBody CreateTariffRequest request){

        return tariffService.create(request);
    }

    @PutMapping("/{code}")
    public TariffResponse update(
            @PathVariable String code,
            @Valid @RequestBody UpdateTariffRequest request){

        return tariffService.update(code,request);
    }*/

    @GetMapping("/{code}")
    public TariffResponse findByCode(
            @PathVariable String code){

        return tariffService.findByCode(code);
    }

    @GetMapping
    public List<TariffResponse> findAll(){

        return tariffService.findAll();
    }

   /* @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String code){

        tariffService.delete(code);
    }*/

}
