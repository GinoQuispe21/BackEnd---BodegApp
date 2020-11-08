package com.bodegapp.demo.service;

import com.bodegapp.demo.exception.ResourceNotFoundException;
import com.bodegapp.demo.model.Delivery;
import com.bodegapp.demo.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public ResponseEntity<?> deleteDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", deliveryId));
        deliveryRepository.delete(delivery);
        return ResponseEntity.ok().build();
    }

    @Override
    public Delivery updateDelivery(Long deliveryId, Delivery deliveryRequest) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", deliveryId));
        delivery.setTypeDelivery(deliveryRequest.getTypeDelivery());
        delivery.setDeliveryPrice(deliveryRequest.getDeliveryPrice());
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery", "Id", deliveryId));
    }

    @Override
    public Page<Delivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }
}
