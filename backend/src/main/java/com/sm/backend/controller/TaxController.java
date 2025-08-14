package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.TaxRequest;
import com.sm.backend.response.TaxResponse;
import com.sm.backend.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tax")
public class TaxController {
    private final TaxService taxService;

    @Autowired
    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

   @GetMapping("/getAll")
    public List<TaxResponse> findAll(){
        return taxService.findAll();
   }
   @GetMapping("/findId/{id}")
    public TaxResponse findById (@PathVariable Long id){
        return taxService.findById(id);
   }
   @DeleteMapping("/deleteById/{id}")

    public void deleteById (@PathVariable Long id){
       try{
            taxService.deleteById(id);
        }catch (ResourceNotFoundException e){
           throw new ResourceNotFoundException("Error "+e.getMessage());
       }
   }
//   @PutMapping("/updateById/{id}")
//    public Object updateById (@PathVariable Long id, @RequestBody TaxRequest request){
//        return taxService.updateById(id,request);
//   }
}
