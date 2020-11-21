package com.bodegapp.demo.service;

import com.bodegapp.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> deleteCustomer(Long userId, Long customerId);
    Customer updateCustomer(Long userId, Long customerId, Customer customerDetail);
    Customer createCustomer(Long userId, Customer customer);
    Customer getCustomerByIdAndUserId(Long userId, Long customerId);
    Customer getCustomerByDniAndUserId(Long userId, int dni);
    Page<Customer> getAllCustomersByUserId(Long userId, Pageable pageable);
    Page<Customer> getAllCustomersActiveByUserId(Long userId, Pageable pageable);
}
