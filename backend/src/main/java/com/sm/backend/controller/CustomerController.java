package com.sm.backend.controller;


import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Customer;
import com.sm.backend.response.CustomerResponse;
import com.sm.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
