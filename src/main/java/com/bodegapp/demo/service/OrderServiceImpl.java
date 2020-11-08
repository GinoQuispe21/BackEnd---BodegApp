package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.CartLine;
import com.bodegapp.demo.model.Order;
import com.bodegapp.demo.model.OrderDetail;
import com.bodegapp.demo.model.Product;
import com.bodegapp.demo.repository.CustomerRepository;
import com.bodegapp.demo.repository.OrderDetailRepository;
import com.bodegapp.demo.repository.OrderRepository;
import com.bodegapp.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.spi.ResolveResult;
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
        return customerRepository.findById(customerId).map(customer -> {
            order.setCustomer(customer);
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException( "CustomerId", "Id", customerId));
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
