package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.CustomerAccount;
import com.bodegapp.demo.repository.CustomerAccountRepository;
import com.bodegapp.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<?> deleteCustomerAccount(Long customerAccountId) {
        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId).orElseThrow(() -> new ResourceNotFoundException("CustomerAccount", "Id", customerAccountId));
        customerAccountRepository.delete(customerAccount);
        return ResponseEntity.ok().build();
    }

    @Override
    public CustomerAccount updateCustomerAccount(Long customerId, CustomerAccount customerAccountRequest) {
        if(!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer", "Id", customerId);

        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);
        customerAccount.setCurrentBalance(customerAccountRequest.getCurrentBalance());
        customerAccount.setCredit(customerAccountRequest.getCredit());
        customerAccount.setInterestRate(customerAccountRequest.getInterestRate());
        customerAccount.setInterestRateType(customerAccountRequest.getInterestRateType());
        customerAccount.setCompounding(customerAccountRequest.getCompounding());
        customerAccount.setTypeYear(customerAccountRequest.getTypeYear());
        return customerAccountRepository.save(customerAccount);
    }

    @Override
    public CustomerAccount createCustomerAccount(Long customerId, CustomerAccount customerAccount) {
        return customerRepository.findById(customerId).map(customer -> {
            customerAccount.setCustomer(customer);
            return customerAccountRepository.save(customerAccount);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
    }

    @Override
    public CustomerAccount getCustomerAccountById(Long customerAccountId) {
        return customerAccountRepository.findById(customerAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerAccount", "Id", customerAccountId));
    }

    @Override
    public CustomerAccount getCustomerAccountByCustomerId(Long customerId) {
        return customerAccountRepository.findByCustomerId(customerId);
    }

    @Override
    public Page<CustomerAccount> getAllCustomerAccount(Pageable pageable) {
        return customerAccountRepository.findAll(pageable);
    }
}
