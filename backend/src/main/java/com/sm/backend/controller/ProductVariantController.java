package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.reasponseHandler.ResponseHandler;
import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productVariant")
public class ProductVariantController {
private final ProductVariantService service;
@Autowired
    public ProductVariantController(ProductVariantService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody ProductVariantRequest request){
    service.register(request);
}
@GetMapping("/getAll")
    public ResponseEntity<?>getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false, defaultValue = "variantName") String sortby,
                                   @RequestParam(required = false, defaultValue = "asc") String sortDir){
    try{
   return ResponseHandler.responseHandler("there is list", HttpStatus.OK,service.getAll(pageNumber,pageSize,sortby,sortDir));
    } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
@GetMapping("/findById/{variantId}")
    public ResponseEntity<?>findById(@PathVariable Long  variantId){
    try{
        return ResponseHandler.responseHandler("id Found",HttpStatus.OK,service.findById(variantId));
    }
    catch (Exception e){
        throw  new ResourceNotFoundException(e.getMessage());
    }
}
@PutMapping("/updateVariant/{variantId}")
    public ResponseEntity<?>updateVariant(@RequestBody ProductVariantRequest request, @PathVariable Long variantId){
    try{
        return ResponseHandler.responseHandler("Updated",HttpStatus.OK,service.updateVariant(request,variantId));
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


    @DeleteMapping("/delete/{variantId}")
    public void delete(@PathVariable Long variantId){
       service.delete(variantId);
        }

    }



