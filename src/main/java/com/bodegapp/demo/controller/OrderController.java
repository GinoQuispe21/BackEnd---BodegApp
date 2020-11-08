package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Order;
import com.bodegapp.demo.resource.OrderResource;
import com.bodegapp.demo.resource.SaveOrderResource;
import com.bodegapp.demo.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() { return orderService.getAllOrders(); }

    @GetMapping("/customers/{customerId}/orders")
    public Page<OrderResource> getAllOrdersByCustomerId(@PathVariable(name = "customerId") Long customerId, Pageable pageable) {
        Page<Order> orderPage = orderService.getAllOrdersByCustomerId(customerId, pageable);
        List<OrderResource> resources = orderPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public OrderResource getOrderByIdAndCustomerId(@PathVariable(name = "customerId") Long customerId, @PathVariable(name = "orderId") Long orderId) {
        return convertToResource(orderService.getOrderByIdAndCustomerId(customerId, orderId));
    }

    @PostMapping("/customers/{customerId}/orders")
    public OrderResource createOrder(@PathVariable(name = "customerId") Long customerId, @Valid @RequestBody SaveOrderResource resource) {
        return convertToResource(orderService.createOrder(customerId, convertToEntity(resource)));
    }

    @PutMapping("/customers/{customerId}/orders/{orderId}")
    public OrderResource updateOrder(@PathVariable(name = "customerId") Long customerId, @PathVariable(name = "orderId") Long orderId, @Valid @RequestBody SaveOrderResource resource) {
        return convertToResource(orderService.updateOrder(customerId, orderId, convertToEntity(resource)));
    }

    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "customerId") Long customerId, @PathVariable(name = "orderId") Long orderId) {
        return orderService.deleteOrder(customerId, orderId);
    }

    private Order convertToEntity(SaveOrderResource resource) { return mapper.map(resource, Order.class); }

    private OrderResource convertToResource(Order entity) { return mapper.map(entity, OrderResource.class); }
}
