package com.sm.backend.controller;

import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.service.ReturnService;
import com.sm.backend.serviceImpl.ReturnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/return")
@CrossOrigin(origins = "*")
public class ReturnController {
    private final ReturnService returnServiceImpl;
    @Autowired
    public ReturnController(ReturnService returnServiceImpl) {
        this.returnServiceImpl = returnServiceImpl;
    }

    @PostMapping("/create")
    public void createReturnOrder (@RequestBody ReturnOrderRequest request){
        returnServiceImpl.createReturnOrder(request);
    }
    @GetMapping("/getAll")
    public List<ReturnOrderResponse> findAll (){
        return returnServiceImpl.findAll();
    }

}
