package com.bodegapp.demo.repository;

import com.bodegapp.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Page<Customer> findByUserId(Long userId, Pageable pageable);
    Optional<Customer> findByIdAndUserId(Long id, Long userId);
}
