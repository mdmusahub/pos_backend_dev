package com.sm.backend.controller;

import com.sm.backend.request.ReturnOrderRequest;
import com.sm.backend.response.ReturnOrderResponse;
import com.sm.backend.serviceImpl.ReturnOrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/returnOrder")
@CrossOrigin(origins = "*")
public class ReturnOrderController {
    private final ReturnOrderServiceImp returnOrderServiceImp;
    @Autowired
    public ReturnOrderController(ReturnOrderServiceImp returnOrderServiceImp) {
        this.returnOrderServiceImp = returnOrderServiceImp;
    }
    @GetMapping("/getAll")
    public List<ReturnOrderResponse> findAll (){
        return returnOrderServiceImp.findAll();
    }
    @PostMapping("/create")
    public void createReturnOrder (ReturnOrderRequest request){
        returnOrderServiceImp.createReturnOrder(request);
    }
    @GetMapping("/findById/{id}")
    public ReturnOrderResponse findById (@PathVariable Long id){
        return returnOrderServiceImp.findById(id);
    }
}
