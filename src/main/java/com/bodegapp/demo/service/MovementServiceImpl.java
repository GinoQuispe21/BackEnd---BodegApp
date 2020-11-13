package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Customer;
import com.bodegapp.demo.model.CustomerAccount;
import com.bodegapp.demo.model.Movement;
import com.bodegapp.demo.model.Order;
import com.bodegapp.demo.repository.CustomerAccountRepository;
import com.bodegapp.demo.repository.CustomerRepository;
import com.bodegapp.demo.repository.MovementRepository;
import com.bodegapp.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovementServiceImpl implements MovementService{
    /*
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public CustomerAccountRepository customerAccountRepository;

    @Autowired
    public MovementRepository movementRepository;

    @Autowired
    public CustomerRepository customerRepository;

    @Override
    public Movement getMovementByIdAndCustomerAccountId(Long movementId, Long customerAccountId) {
        return null;
    }

    @Override
    public Page<Movement> getAllMovementsByCustomerAccountId(Long customerAccountId, Pageable pageable) {
        return null;
    }

    @Override
    public Movement createOrderMovement(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException( "OrderId", "Id", orderId));
        Long customerId = order.getCustomer().getId();
        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);
        Movement movement = new Movement();

        movement.setAmount(order.getPayment());
        movement.setGenerated_date(order.getGenerated_date());
        movement.setCustomerAccount(customerAccount);
        movement.setOrder(order);

        order.setMovement(movement);
        orderRepository.save(order);

        customerAccount.getMovements().add(movement);
        customerAccountRepository.save(customerAccount);

        movementRepository.save(movement);

        return movement;
    }

    @Override
    public Movement createPaymentMovement(Long OrderId) {
        return null;
    }*/
}
