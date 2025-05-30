package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.reasponseHandler.ResponseHandler;
import com.sm.backend.request.OrderRequest;
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

    @PostMapping("/addOrder")
    public void register(@RequestBody OrderRequest request){
    service.register(request);
}
@GetMapping("/getAll")
    public ResponseEntity<?>getAllOrders(){
    try{
        return ResponseHandler.responseHandler("orders retrieved successfully.", HttpStatus.OK,service.getAllOrders());
    } catch (Exception e) {
        throw new ResourceNotFoundException("cannot retrieve orders.");
    }
}
@GetMapping("/findById/{orderId}")
    public ResponseEntity<?>findById(@PathVariable Long orderId){
    try {
        return ResponseHandler.responseHandler("id retrieve successfully",HttpStatus.OK,service.findById(orderId));
    }catch (Exception e){
        throw new ResourceNotFoundException("id not Found");
    }
}
@PutMapping("/update/{orderId}")
    public void  updateById (@PathVariable Long orderId,OrderRequest request){
        service.updateById(orderId,request);
    }
@DeleteMapping("/delete/{orderId}")
    public void deleteById(@PathVariable Long orderId){
    service.deleteById(orderId);
}

}





