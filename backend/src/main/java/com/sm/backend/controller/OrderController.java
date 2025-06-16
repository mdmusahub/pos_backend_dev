package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.OrderRequest;
import com.sm.backend.response.OrderResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<List<OrderResponse>>getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                     @RequestParam(required = false, defaultValue = "orderId") String sortby,
                                                     @RequestParam(required = false, defaultValue = "asc") String sortDir){
    try{
        return new ResponseEntity<>(service.getAll(pageNumber,pageSize,sortby,sortDir), HttpStatus.OK);
    } catch (Exception e) {
        throw new ResourceNotFoundException("cannot retrieve orders.");
    }
}
@GetMapping("/getById/{orderId}")
    public ResponseEntity<OrderResponse>getById(@PathVariable Long orderId){
    try {
        return new ResponseEntity<>(service.getById(orderId),HttpStatus.OK);
    }catch (Exception e){
        throw new ResourceNotFoundException("invalid Order ID");
    }
}
@DeleteMapping("/delete/{orderId}")
    public void delete(@PathVariable Long orderId){
    service.delete(orderId);
}

}





