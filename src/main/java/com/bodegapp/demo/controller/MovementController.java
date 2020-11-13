package com.bodegapp.demo.controller;

import com.bodegapp.demo.model.Delivery;
import com.bodegapp.demo.model.Movement;
import com.bodegapp.demo.resource.DeliveryResource;
import com.bodegapp.demo.resource.MovementResource;
import com.bodegapp.demo.resource.SaveDeliveryResource;
import com.bodegapp.demo.resource.SaveMovementResource;
import com.bodegapp.demo.service.MovementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MovementController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MovementService movementService;

    /*@PostMapping("/orders/{orderId}/movements")
    public MovementResource createOrderMovement(@PathVariable(name = "orderId") Long orderId) {
        return convertToResource(movementService.createOrderMovement(orderId));
    }*/

    private Movement convertToEntity(SaveMovementResource resource) { return mapper.map(resource, Movement.class); }

    private MovementResource convertToResource(Movement entity) { return mapper.map(entity, MovementResource.class); }

}
