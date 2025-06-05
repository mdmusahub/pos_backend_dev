package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;

import com.sm.backend.responseHandler.ResponseHandler;
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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseHandler.responseHandler("there is list", HttpStatus.OK, service.getAll());

        } catch (Exception e) {
            throw new ResourceNotFoundException("something went Wrong");
        }
    }

    @GetMapping("/getById/{orderItemId}")
    public ResponseEntity<?> getById(@PathVariable Long orderItemId) {
        try {
            return ResponseHandler.responseHandler("Order item retrieved successfully", HttpStatus.OK, service.getById(orderItemId));
        } catch (Exception e) {
            throw new ResourceNotFoundException("invalid order item ID");
        }
    }
}

