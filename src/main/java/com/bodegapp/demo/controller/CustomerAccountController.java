package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.CustomerAccount;
import com.bodegapp.demo.resource.CustomerAccountResource;
import com.bodegapp.demo.resource.SaveCustomerAccountResource;
import com.bodegapp.demo.service.CustomerAccountService;
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
public class CustomerAccountController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerAccountService customerAccountService;

    @GetMapping("/customerAccounts")
    public Page<CustomerAccountResource> getAllCustomerAccount(Pageable pageable) {
        Page<CustomerAccount> customerAccountPage = customerAccountService.getAllCustomerAccount(pageable);
        List<CustomerAccountResource> resources = customerAccountPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/customerAccounts/{id}")
    public CustomerAccountResource getCustomerAccountById(@PathVariable(name = "id") Long customerAccountId) {
        return convertToResource(customerAccountService.getCustomerAccountById(customerAccountId));
    }

    @PostMapping("/customers/{customerId}/customerAccounts")
    public CustomerAccountResource createCustomerAccount(@PathVariable(name = "customerId") Long customerId, @Valid @RequestBody SaveCustomerAccountResource resource) {
        CustomerAccount customerAccount = convertToEntity(resource);
        return convertToResource(customerAccountService.createCustomerAccount(customerId, customerAccount));
    }

    @PutMapping("customers/{customerId}/customerAccounts")
    public CustomerAccountResource updateCustomerAccount(@PathVariable(name = "customerId") Long customerId, @Valid @RequestBody SaveCustomerAccountResource resource) {
        return convertToResource(customerAccountService.updateCustomerAccount(customerId, convertToEntity(resource)));
    }

    @DeleteMapping("/customerAccounts/{id}")
    public ResponseEntity<?> deleteCustomerAccounts(@PathVariable(name = "id") Long customerAccountId) {
        return customerAccountService.deleteCustomerAccount(customerAccountId);
    }

    @GetMapping("customers/{customerId}/customerAccounts")
    public CustomerAccountResource getCustomerAccountByCustomerId(@PathVariable(name = "customerId") Long customerId) {
        CustomerAccount customerAccount = customerAccountService.getCustomerAccountByCustomerId(customerId);
        return convertToResource(customerAccount);
    }

    private CustomerAccount convertToEntity(SaveCustomerAccountResource resource) { return mapper.map(resource, CustomerAccount.class); }

    private CustomerAccountResource convertToResource(CustomerAccount entity) { return mapper.map(entity, CustomerAccountResource.class); }
}
