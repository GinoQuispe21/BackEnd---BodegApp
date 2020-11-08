package com.bodegapp.demo.repository;

import com.bodegapp.demo.model.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    Page<Movement> findByCustomerAccountId(Long customerAccountId, Pageable pageable);
    Optional<Movement> findByIdAndCustomerAccountId(Long id, Long customerAccountId);
}
