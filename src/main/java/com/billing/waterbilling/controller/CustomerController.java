package com.billing.waterbilling.controller;

//GET /customers
//
//GET /customers/{id}
//
//POST /customers
//
//PUT /customers/{id}
//
//DELETE /customers/{id}

import com.billing.waterbilling.dto.request.CreateCustomerRequest;
import com.billing.waterbilling.dto.request.UpdateCustomerRequest;
import com.billing.waterbilling.dto.response.CustomerResponse;
import com.billing.waterbilling.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CreateCustomerRequest request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.create(request));
    }

    @PutMapping("/{customerId}")
    public CustomerResponse update(
            @PathVariable String customerId,
            @Valid @RequestBody UpdateCustomerRequest request){

        return customerService.update(customerId,request);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse findById(
            @PathVariable String customerId){

        return customerService.findById(customerId);
    }

    @GetMapping
    public List<CustomerResponse> findAll(){

        return customerService.findAll();
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String customerId){

        customerService.delete(customerId);
    }

}
