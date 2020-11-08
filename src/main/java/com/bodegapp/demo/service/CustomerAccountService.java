package com.bodegapp.demo.service;

import com.bodegapp.demo.model.CustomerAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CustomerAccountService {
    ResponseEntity<?> deleteCustomerAccount(Long customerAccountId);
    CustomerAccount updateCustomerAccount(Long customerId, CustomerAccount customerAccountRequest);
    CustomerAccount createCustomerAccount(Long customerId, CustomerAccount customerAccount);
    CustomerAccount getCustomerAccountById(Long customerAccountId);
    CustomerAccount getCustomerAccountByCustomerId(Long customerId);
    Page<CustomerAccount> getAllCustomerAccount(Pageable pageable);
}
