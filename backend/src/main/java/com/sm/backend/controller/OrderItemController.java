package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;

import com.sm.backend.response.OrderItemResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItem")
@CrossOrigin(origins = "*")
public class OrderItemController {
    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderItemResponse>> getAll() {
        try {
            return new ResponseEntity<>(service.getAll(),HttpStatus.OK);

        } catch (Exception e) {
            throw new ResourceNotFoundException("something went Wrong");
        }
    }

    @GetMapping("/getById/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long orderItemId) {
        try {
            return new ResponseEntity<>(service.getById(orderItemId),HttpStatus.OK) ;
        } catch (Exception e) {
            throw new ResourceNotFoundException("invalid order item ID");
        }
    }
}

