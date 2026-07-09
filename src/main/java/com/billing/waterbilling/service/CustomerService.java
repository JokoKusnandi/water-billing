package com.billing.waterbilling.service;


import com.billing.waterbilling.dto.request.CreateCustomerRequest;
import com.billing.waterbilling.dto.request.UpdateCustomerRequest;
import com.billing.waterbilling.dto.response.CustomerResponse;
import com.billing.waterbilling.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    CustomerResponse update(String customerId, UpdateCustomerRequest request);

    Customer findEntityById(String customerId);

    CustomerResponse findById(String customerId);

    List<CustomerResponse> findAll();

    void delete(String customerId);

}
