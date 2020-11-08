package com.bodegapp.demo.service;

import com.bodegapp.demo.model.Customer;
import com.bodegapp.demo.model.Movement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovementService {
    Movement getMovementByIdAndCustomerAccountId(Long movementId, Long customerAccountId);
    Page<Movement> getAllMovementsByCustomerAccountId(Long customerAccountId, Pageable pageable);
    Movement createMovement(Long customerAccountId, Movement movement);
    Movement updateMovement(Long userId, Long customerId, Customer customerDetail);
}
