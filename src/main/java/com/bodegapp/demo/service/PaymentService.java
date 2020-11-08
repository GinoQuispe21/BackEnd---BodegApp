package com.bodegapp.demo.service;

import com.bodegapp.demo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<?> deletePayment(Long customerId, Long paymentId);
    Payment updatePayment(Long customerId, Long paymentId, Payment paymentDetails);
    Payment createPayment(Long customerId, Payment payment);
    Payment getPaymentByIdAndCustomerId(Long customerId, Long paymentId);
    Page<Payment> getAllPaymentsByCustomerId(Long customerId, Pageable pageable);
    List<Payment> getAllPayments();
}
