package com.bodegapp.demo.repository;

import com.bodegapp.demo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Page<Payment> findByCustomerId(Long customerId, Pageable pageable);
    Optional<Payment> findByIdAndCustomerId(Long id, Long customerId);
}
