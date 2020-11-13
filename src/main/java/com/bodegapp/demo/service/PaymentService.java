package com.bodegapp.demo.service;

import com.bodegapp.demo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<?> deletePayment(Long customerAccountId, Long paymentId);
    Payment updatePayment(Long customerAccountId, Long paymentId, Payment paymentDetails);
    Payment createPayment(Long customerAccountId, Payment payment);
    Payment getPaymentByIdAndCustomerId(Long customerAccountId, Long paymentId);
    Page<Payment> getAllPaymentsByCustomerAccountId(Long customerAccountId, Pageable pageable);
    List<Payment> getAllPayments();
}
