package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
private final OrderService service;
@Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderRequest request){
         service.createOrder(request);

    }
@GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
    try{
        return ResponseHandler.responseHandler("orders retrieved successfully.", HttpStatus.OK,service.getAll());
    } catch (Exception e) {
        throw new ResourceNotFoundException("cannot retrieve orders.");
    }
}
@GetMapping("/getById/{orderId}")
    public ResponseEntity<?>getById(@PathVariable Long orderId){
    try {
        return ResponseHandler.responseHandler("id retrieve successfully",HttpStatus.OK,service.getById(orderId));
    }catch (Exception e){
        throw new ResourceNotFoundException("invalid Order ID");
    }
}
@DeleteMapping("/delete/{orderId}")
    public void delete(@PathVariable Long orderId){
    service.delete(orderId);
}

}





