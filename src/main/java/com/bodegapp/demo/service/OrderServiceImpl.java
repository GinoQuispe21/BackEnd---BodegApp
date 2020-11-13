package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.*;
import com.bodegapp.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Override
    public ResponseEntity<?> deleteOrder(Long customerId, Long orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Order not found with Id" + orderId + " and CustomerId " + customerId));
    }

    @Override
    public Order updateOrder(Long customerId, Long orderId, Order orderDetails) {
        if (!customerRepository.existsById(customerId))
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        return orderRepository.findById(orderId).map(order -> {
            order.setGenerated_date(orderDetails.getGenerated_date());
            order.setPayment(orderDetails.getPayment());
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
    }

    @Override
    public Order createOrder(Long customerId, Order order) {

        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);

        return customerRepository.findById(customerId).map(customer -> {
            order.setCustomer(customer);

            double capital;
            double year = 0;
            double valorFuturo;
            double interes;
            double cantPeri = 0;

            double amount = order.getPayment();

            if(customerAccount.getCurrentBalance() == 0.00){
                customerAccount.setCurrentBalance(amount);
                customerAccount.setFirstDate(order.getGenerated_date());
                //Asignar la fecha de la compra como la nueva fecha inicial para el siguiente periodo
            }
            else {

                capital = customerAccount.getCurrentBalance();

                if (customerAccount.getTypeYear() == 1) {
                    year = 360;
                }
                if (customerAccount.getTypeYear() == 2) {
                    year = 365;
                }

                //Recibir  la fecha del anterior movimiento y recibir la fecha actual
                Date lastDate = order.getGenerated_date();
                Date firstDate = customerAccount.getFirstDate();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                String fd = formatter.format(firstDate);
                String ld = formatter.format(lastDate);

                LocalDate d1 = LocalDate.parse(fd, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate d2 = LocalDate.parse(ld, DateTimeFormatter.ISO_LOCAL_DATE);
                Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
                long dif = diff.toDays();

                switch (customerAccount.getInterestRatePeriod()) {
                    case 1:
                        cantPeri = 30;
                    case 2:
                        cantPeri = 60;
                    case 3:
                        cantPeri = 90;
                    case 4:
                        cantPeri = 120;
                    case 5:
                        cantPeri = 180;
                    case 6:
                        cantPeri = 360;
                }

                if (amount + customerAccount.getCurrentBalance() <= customerAccount.getCredit()) {
                    if (customerAccount.getInterestRateType() == 1) {

                        //Interes Simple
                        interes = capital * customerAccount.getInterestRate() * (year / cantPeri) * (dif) / year;
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                    }
                    if (customerAccount.getInterestRateType() == 2) {

                        //Interes Nominal
                        //60 = m Capitalizacion, caso ejemplo Bimestral
                        interes = capital * (Math.pow((1 + (customerAccount.getInterestRate() / 60)), (dif)) - 1);
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                    }
                    if (customerAccount.getInterestRateType() == 3) {

                        //Interes Efectivo
                        interes = capital * (Math.pow(1 + customerAccount.getInterestRate(), (dif) / (year / cantPeri)) - 1);
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                    }
                } else {
                    return null;
                }
            }
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException( "CustomerId", "Id", customerId));

        /*Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException( "CustomerId", "Id", customerId));
        CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customerId);

        //Order order1 = order;

        Movement movement = new Movement();

        movement.setAmount(order.getPayment());
        movement.setGenerated_date(order.getGenerated_date());
        movement.setCustomerAccount(customerAccount);
        movement.setOrder(order);

        movementRepository.save(movement);

        order.setMovement(movement);
        order.setCustomer(customer);
        orderRepository.save(order);

        customerAccount.getMovements().add(movement);
        customerAccountRepository.save(customerAccount);

        return order;*/

    }

    @Override
    public Order getOrderByIdAndCustomerId(Long customerId, Long orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with Id" + orderId + " and CustomerId " + customerId
                ));
    }
    @Override
    public Page<Order> getAllOrdersByCustomerId(Long customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    @Override
    public Order AssignProductsByOrderId(Long orderId, List<CartLine> products) {
        Order order =  orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order", "Id", orderId));
        for(CartLine product : products){
            OrderDetail orderDetail = new OrderDetail();
            Product product1 = productRepository.findById(product.getId()).orElseThrow(() -> new ResourceNotFoundException("Product", "Id",product.getId()));
            orderDetail.setProduct(product1);
            orderDetail.setOrder(order);
            orderDetail.setQuantity(product.getQuantity());

            product1.getOrderDetails().add(orderDetail);
            order.getOrderDetails().add(orderDetail);

            orderDetailRepository.save(orderDetail);
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
