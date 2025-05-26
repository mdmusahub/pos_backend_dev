package com.sm.backend.controller;

import com.sm.backend.request.ProductVariantRequest;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productVariant")
public class ProductVariantController {
    private final ProductVariantService variantService;
@Autowired
    public ProductVariantController(ProductVariantService variantService) {
        this.variantService = variantService;
    }

    @PostMapping("/addVariant")
    public void addVariant(@RequestBody ProductVariantRequest request){
    variantService.addVariant(request);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllVariants(){
    try{
        return ResponseHandler.responseBuilder("successfully retrived all variants.",
                HttpStatus.OK,variantService.getAllVariants());
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getVariantById(@PathVariable Long id){
    try {
        return ResponseHandler.responseBuilder("variant successfully retrieved by id.",
                HttpStatus.OK,variantService.GetById(id));
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody ProductVariantRequest request){
try {
    return ResponseHandler.responseBuilder("variant updated successfully.",
            HttpStatus.OK,variantService.updateById(id,request));
} catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}

    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
    variantService.delete(id);
    }

}
