package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Delivery;
import com.bodegapp.demo.resource.DeliveryResource;
import com.bodegapp.demo.resource.OrderResource;
import com.bodegapp.demo.resource.SaveDeliveryResource;
import com.bodegapp.demo.resource.UserResource;
import com.bodegapp.demo.service.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DeliveryController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/deliveries")
    public Page<DeliveryResource> getAllDeliveries(Pageable pageable) {
        Page<Delivery> deliveryPage = deliveryService.getAllDeliveries(pageable);
        List<DeliveryResource> resources = deliveryPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/deliveries/{id}")
    public DeliveryResource getDeliveryById(@PathVariable(name = "id") Long deliveryId) {
        return convertToResource(deliveryService.getDeliveryById(deliveryId));
    }

    @PostMapping("/deliveries")
    public DeliveryResource createDelivery(@Valid @RequestBody SaveDeliveryResource resource)  {
        Delivery delivery = convertToEntity(resource);
        return convertToResource(deliveryService.createDelivery(delivery));
    }

    @PutMapping("/deliveries/{id}")
    public DeliveryResource updateDelivery(@PathVariable(name = "id") Long deliveryId, @Valid @RequestBody SaveDeliveryResource resource) {
        Delivery delivery = convertToEntity(resource);
        return convertToResource(deliveryService.updateDelivery(deliveryId, delivery));
    }

    @DeleteMapping("/deliveries/{id}")
    public ResponseEntity<?> deleteDelivery(@PathVariable(name = "id") Long deliveryId) {
        return deliveryService.deleteDelivery(deliveryId);
    }

    private Delivery convertToEntity(SaveDeliveryResource resource) { return mapper.map(resource, Delivery.class); }

    private DeliveryResource convertToResource(Delivery entity) { return mapper.map(entity, DeliveryResource.class); }
}
