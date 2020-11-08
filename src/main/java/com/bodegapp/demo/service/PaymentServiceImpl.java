package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Payment;
import com.bodegapp.demo.repository.CustomerRepository;
import com.bodegapp.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<?> deletePayment(Long customerId, Long paymentId) {
        return paymentRepository.findByIdAndCustomerId(paymentId, customerId).map(payment -> {
            paymentRepository.delete(payment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Payment not found with Id" + paymentId + " and CustomerId " + customerId));
    }

    @Override
    public Payment updatePayment(Long customerId, Long paymentId, Payment paymentDetails) {
        if (!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setGenerated_date(paymentDetails.getGenerated_date());
            payment.setPayment(paymentDetails.getPayment());
            return paymentRepository.save(payment);
        }).orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
    }

    @Override
    public Payment createPayment(Long customerId, Payment payment) {
        return customerRepository.findById(customerId).map(customer -> {
            payment.setCustomer(customer);
            return paymentRepository.save(payment);
        }).orElseThrow(() -> new ResourceNotFoundException( "CustomerId", "Id", customerId));
    }

    @Override
    public Payment getPaymentByIdAndCustomerId(Long customerId, Long paymentId) {
        return paymentRepository.findByIdAndCustomerId(paymentId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with Id" + paymentId + " and CustomerId " + customerId));
    }

    @Override
    public Page<Payment> getAllPaymentsByCustomerId(Long customerId, Pageable pageable) {
        return paymentRepository.findByCustomerId(customerId, pageable);
    }

    @Override
    public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
}
