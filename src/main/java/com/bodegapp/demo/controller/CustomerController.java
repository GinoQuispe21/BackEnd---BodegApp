package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Customer;
import com.bodegapp.demo.resource.CustomerResource;
import com.bodegapp.demo.resource.SaveCustomerResource;
import com.bodegapp.demo.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/users/{userId}/customers")
    public Page<CustomerResource> getAllCustomersByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<Customer> customerPage = customerService.getAllCustomersByUserId(userId, pageable);
        List<CustomerResource> resources = customerPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/customersActives")
    public Page<CustomerResource> getAllCustomersActivesByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        Page<Customer> customerPage = customerService.getAllCustomersActiveByUserId(userId, pageable);
        List<CustomerResource> resources = customerPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/users/{userId}/customers/{customerId}")
    public CustomerResource getCustomerByIdAndUserId(@PathVariable(name = "userId") Long userId, @PathVariable(name = "customerId") Long customerId) {
        return convertToResource(customerService.getCustomerByIdAndUserId(userId, customerId));
    }

    @GetMapping("/users/{userId}/customersDni/{customerDNI}")
    public CustomerResource getCustomerByDniAndUserId(@PathVariable(name = "userId") Long userId, @PathVariable(name = "customerDNI") int customerDni) {
        return convertToResource(customerService.getCustomerByDniAndUserId(userId, customerDni));
    }

    @PostMapping("/users/{userId}/customers")
    public CustomerResource createCustomer(@PathVariable(name = "userId") Long userId, @Valid @RequestBody SaveCustomerResource resource) {

        return convertToResource(customerService.createCustomer(userId, convertToEntity(resource)));
    }

    @PutMapping("/users/{userId}/customers/{customerId}")
    public CustomerResource updateCustomer(@PathVariable(name = "userId") Long userId, @PathVariable(name = "customerId") Long customerId, @Valid @RequestBody SaveCustomerResource resource) {
        return convertToResource(customerService.updateCustomer(userId, customerId, convertToEntity(resource)));
    }

    @DeleteMapping("/users/{userId}/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "userId") Long userId, @PathVariable(name = "customerId") Long customerId) {
        return customerService.deleteCustomer(userId, customerId);
    }

    private Customer convertToEntity(SaveCustomerResource resource) { return mapper.map(resource, Customer.class); }

    private CustomerResource convertToResource(Customer entity) { return mapper.map(entity, CustomerResource.class); }
}
