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
            double capitalizacion =  0;
            double gain = customerAccount.getGain();

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
                double dif = diff.toDays();

                if(customerAccount.getInterestRatePeriod() == 1){
                    cantPeri = 30;
                }
                if(customerAccount.getInterestRatePeriod() == 2){
                    cantPeri = 60;
                }
                if(customerAccount.getInterestRatePeriod() == 3){
                    cantPeri = 90;
                }
                if(customerAccount.getInterestRatePeriod() == 4){
                    cantPeri = 120;
                }
                if(customerAccount.getInterestRatePeriod() == 5){
                    cantPeri = 180;
                }
                if(customerAccount.getInterestRatePeriod() == 6){
                    cantPeri = 360;
                }

                if(customerAccount.getCompounding() == 1){
                    capitalizacion = 1;
                }
                if(customerAccount.getCompounding() == 2){
                    capitalizacion = 7;
                }
                if(customerAccount.getCompounding() == 3){
                    capitalizacion = 30;
                }
                if(customerAccount.getCompounding() == 4){
                    capitalizacion = 60;
                }
                if(customerAccount.getCompounding() == 5){
                    capitalizacion = 90;
                }
                if(customerAccount.getCompounding() == 6){
                    capitalizacion = 120;
                }
                if(customerAccount.getCompounding() == 7){
                    capitalizacion = 180;
                }

                if (amount + customerAccount.getCurrentBalance() <= customerAccount.getCredit()) {
                    if (customerAccount.getInterestRateType() == 1) {
                        //Interes Simple
                        interes = capital * customerAccount.getInterestRate() * (year / cantPeri) * (dif) / year;
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                        customerAccount.setGain(gain + interes);
                        customerAccount.setAvailableBalance(customerAccount.getCredit() - customerAccount.getCurrentBalance());
                    }
                    if (customerAccount.getInterestRateType() == 2) {

                        //Interes Nominal
                        //m = year/cantPeri   n = dif/capitalizacion
                        interes = capital * (Math.pow((1 + (customerAccount.getInterestRate() / (cantPeri/capitalizacion))), (dif/capitalizacion)) - 1);
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                        customerAccount.setGain(gain + interes);
                        customerAccount.setAvailableBalance(customerAccount.getCredit() - customerAccount.getCurrentBalance());
                    }
                    if (customerAccount.getInterestRateType() == 3) {

                        //Interes Efectivo
                        interes = capital * ((Math.pow(1 + customerAccount.getInterestRate(), (dif/cantPeri)))-1);
                        valorFuturo = capital + interes + amount;
                        customerAccount.setCurrentBalance(valorFuturo);
                        customerAccount.setFirstDate(order.getGenerated_date());
                        customerAccount.setGain(gain + interes);
                        customerAccount.setAvailableBalance(customerAccount.getCredit() - customerAccount.getCurrentBalance());
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
