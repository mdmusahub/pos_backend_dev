package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.reasponseHandler.ResponseHandler;
import com.sm.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseHandler.responseHandler("there is list", HttpStatus.OK, service.findAll());

        } catch (Exception e) {
            throw new ResourceNotFoundException("something went Wrong");
        }
    }

    @GetMapping("/finfById/{orderItemId}")
    public ResponseEntity<?> findById(@PathVariable Long orderItemId) {
        try {
            return ResponseHandler.responseHandler("id found", HttpStatus.OK, service.findById(orderItemId));
        } catch (Exception e) {
            throw new ResourceNotFoundException("id not found");
        }
    }
}

