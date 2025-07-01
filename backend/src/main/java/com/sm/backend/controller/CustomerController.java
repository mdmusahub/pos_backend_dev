package com.sm.backend.controller;


import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Customer;
import com.sm.backend.request.CustomerRequest;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
private final CustomerService customerService;
@Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/getById/{id}")
public ResponseEntity<CustomerResponse>getById(@PathVariable Long id){
try {
    return new ResponseEntity<>(customerService.getById(id),HttpStatus.OK);
}
   catch (Exception e){
    throw new ResourceNotFoundException(e.getMessage());
   }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerResponse>> getAll(){
    try{
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    } catch (Exception e) {
        throw new ResourceNotFoundException("cannot retrieve customers");
    }
    }
@PutMapping("/update/{id}")
    public void updateCustomer(@RequestBody CustomerRequest request,@PathVariable Long id){
    try {
        customerService.update(request,id);
    } catch (Exception e) {
        throw new ResourceNotFoundException(e.getMessage());
    }
}
@DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id){
    try {
        customerService.delete(id);
} catch (Exception e) {
        throw new ResourceNotFoundException(e.getMessage());
    }
}


}
