package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Payment;
import com.bodegapp.demo.resource.PaymentResource;
import com.bodegapp.demo.resource.SavePaymentResource;
import com.bodegapp.demo.service.PaymentService;
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

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public List<Payment> getAllPayments() { return paymentService.getAllPayments(); }

    @GetMapping("/customerAccounts/{customerAccountsId}/payments")
    public Page<PaymentResource> getAllPaymentsByCustomerId(@PathVariable(name = "customerAccountsId") Long customerId, Pageable pageable) {
        Page<Payment> paymentPage = paymentService.getAllPaymentsByCustomerAccountId(customerId, pageable);
        List<PaymentResource> resources = paymentPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/customerAccounts/{customerAccountsId}/payments/{paymentId}")
    public PaymentResource getPaymentByIdAndCustomerId(@PathVariable(name = "customerAccountsId") Long customerId, @PathVariable(name = "paymentId") Long paymentId) {
        return convertToResource(paymentService.getPaymentByIdAndCustomerId(customerId, paymentId));
    }

    @PostMapping("/customerAccounts/{customerAccountsId}/payments")
    public PaymentResource createPayment(@PathVariable(name = "customerAccountsId") Long customerId, @Valid @RequestBody SavePaymentResource resource) {
        return convertToResource(paymentService.createPayment(customerId, convertToEntity(resource)));
    }

    @PutMapping("/customerAccounts/{customerAccountsId}/payments/{paymentId}")
    public PaymentResource updatePayment(@PathVariable(name = "customerAccountsId") Long customerId, @PathVariable(name = "paymentId") Long paymentId, @Valid @RequestBody SavePaymentResource resource) {
        return convertToResource(paymentService.updatePayment(customerId, paymentId, convertToEntity(resource)));
    }

    @DeleteMapping("/customerAccounts/{customerAccountsId}/payments/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable(name = "customerAccountsId") Long customerId, @PathVariable(name = "paymentId") Long paymentId) {
        return paymentService.deletePayment(customerId, paymentId);
    }

    private Payment convertToEntity(SavePaymentResource resource) { return mapper.map(resource, Payment.class); }

    private PaymentResource convertToResource(Payment entity) { return mapper.map(entity, PaymentResource.class); }

}
