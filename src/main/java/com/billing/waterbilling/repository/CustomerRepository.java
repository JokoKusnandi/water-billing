package com.billing.waterbilling.repository;

import com.billing.waterbilling.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends
        JpaRepository<Customer,String>,
        JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    boolean existsByEmail(String email);

}
