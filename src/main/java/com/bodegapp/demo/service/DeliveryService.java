package com.bodegapp.demo.service;

import com.bodegapp.demo.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DeliveryService {
    ResponseEntity<?> deleteDelivery(Long deliveryId);
    Delivery updateDelivery(Long deliveryId, Delivery deliveryRequest);
    Delivery createDelivery(Delivery delivery);
    Delivery getDeliveryById(Long deliveryId);
    Page<Delivery> getAllDeliveries(Pageable pageable);

}
