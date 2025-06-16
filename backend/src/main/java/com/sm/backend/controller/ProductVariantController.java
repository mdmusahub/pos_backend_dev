package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.response.ProductVariantResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productVariant")
@CrossOrigin(origins = "*")
public class ProductVariantController {
private final ProductVariantService service;
@Autowired
    public ProductVariantController(ProductVariantService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createVariant(@RequestBody ProductVariantRequest request){
    service.createVariant(request);
}
@GetMapping("/getAll")
    public ResponseEntity<List<ProductVariantResponse>>getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                              @RequestParam(required = false, defaultValue = "variantName") String sortby,
                                                              @RequestParam(required = false, defaultValue = "asc") String sortDir){
    try{
   return new ResponseEntity<>(service.getAll(pageNumber,pageSize,sortby,sortDir),HttpStatus.OK);
    } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
@GetMapping("/getById/{variantId}")
    public ResponseEntity<?>getById(@PathVariable Long  variantId){
    try{
        return ResponseHandler.responseHandler("variant retrieved successfully",HttpStatus.OK,service.getById(variantId));
    }
    catch (Exception e){
        throw  new ResourceNotFoundException(e.getMessage());
    }
}
@PutMapping("/update/{variantId}")
    public ResponseEntity<?>update(@RequestBody ProductVariantRequest request, @PathVariable Long variantId){
    try{
        return ResponseHandler.responseHandler("Variant Updated successfully",HttpStatus.OK,service.update(request,variantId));
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


    @DeleteMapping("/delete/{variantId}")
    public void delete(@PathVariable Long variantId){
       service.delete(variantId);
        }

   }



