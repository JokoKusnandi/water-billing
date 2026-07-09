package com.billing.waterbilling.service.impl;

import com.billing.waterbilling.dto.request.CreateCustomerRequest;
import com.billing.waterbilling.dto.request.UpdateCustomerRequest;
import com.billing.waterbilling.dto.response.CustomerResponse;
import com.billing.waterbilling.entity.Customer;
import com.billing.waterbilling.exception.CustomerNotFoundException;
import com.billing.waterbilling.exception.DuplicateCustomerException;
import com.billing.waterbilling.exception.DuplicateEmailException;
import com.billing.waterbilling.mapper.CustomerMapper;
import com.billing.waterbilling.repository.CustomerRepository;
import com.billing.waterbilling.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {

        log.info("Create customer {}", request.getCustomerId());

        if (repository.existsByCustomerId(request.getCustomerId())) {
            throw new DuplicateCustomerException();
        }

        if (request.getEmail() != null && repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();

        }

        Customer customer = mapper.toEntity(request);

        Customer saved = repository.save(customer);

        log.info("Customer {} created", saved.getCustomerId());

        return mapper.toResponse(saved);

    }

    @Override
    public CustomerResponse update(String customerId, UpdateCustomerRequest request) {

        Customer customer = repository.findByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        mapper.updateEntity(customer, request);

        Customer updated = repository.save(customer);

        log.info("Customer {} updated", customerId);

        return mapper.toResponse(updated);

    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse findById(String customerId) {

        Customer customer = repository.findByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return mapper.toResponse(customer);

    }

    @Override
    @Transactional(readOnly = true)
    public Customer findEntityById(String customerId) {

        return repository.findByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);

    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> findAll() {

        return repository.findAll()

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    @Override
    public void delete(String customerId) {

        Customer customer = repository.findByCustomerId(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        repository.delete(customer);

        log.info("Customer {} deleted", customerId);

    }

}
