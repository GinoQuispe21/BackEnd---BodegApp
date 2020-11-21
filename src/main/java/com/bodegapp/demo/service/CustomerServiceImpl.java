package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Customer;
import com.bodegapp.demo.repository.CustomerAccountRepository;
import com.bodegapp.demo.repository.CustomerRepository;
import com.bodegapp.demo.repository.OrderRepository;
import com.bodegapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public ResponseEntity<?> deleteCustomer(Long userId, Long customerId) {
        return customerRepository.findByIdAndUserId(customerId, userId).map(customer ->{
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(" Customer not found with Id " + customerId + " and UserId " + userId));
    }

    @Override
    public Customer updateCustomer(Long userId, Long customerId, Customer customerDetail) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User", "Id", userId);

        return customerRepository.findById(customerId).map(customer -> {
            customer.setDni(customerDetail.getDni());
            customer.setCustomerName(customerDetail.getCustomerName());
            customer.setCustomerLastname(customerDetail.getCustomerLastname());
            customer.setAddress(customerDetail.getAddress());
            customer.setDistrict(customerDetail.getDistrict());
            customer.setCountry(customerDetail.getCountry());
            customer.setEmail(customerDetail.getEmail());
            customer.setState(customerDetail.getState());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
    }

    @Override
    public Customer createCustomer(Long userId, Customer customer) {
        return userRepository.findById(userId).map(user -> {
            customer.setUser(user);
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public Customer getCustomerByIdAndUserId(Long userId, Long customerId){
        return customerRepository.findByIdAndUserId(customerId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with Id" + customerId +
                                "and UserId " + userId
                ));
    }

    @Override
    public Page<Customer> getAllCustomersByUserId(Long userId, Pageable pageable){
        return customerRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Customer> getAllCustomersActiveByUserId(Long userId, Pageable pageable){
        Page<Customer> customers  = customerRepository.findByUserId(userId, pageable);
        List<Customer> listCustomers = new ArrayList<>();
        for(Customer  customer : customers){
            if(customer.getState() == 0){
                listCustomers.add(customer);
            }
        }
        Page<Customer> customersActives = new PageImpl<>(listCustomers, pageable, listCustomers.size());
        return customersActives;
    }
}
