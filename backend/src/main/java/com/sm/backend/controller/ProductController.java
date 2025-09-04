package com.sm.backend.controller;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.ProductRequest;
import com.sm.backend.request.productUpdateReq.ProductUpdateRequest;
import com.sm.backend.response.productDetailsResponses.ProductVariantInventoryResponse;
import com.sm.backend.response.ProductResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
//    ,allowedHeaders = "*",
//    methods = {RequestMethod.GET,
//            RequestMethod.POST,
//            RequestMethod.PUT,
//            RequestMethod.DELETE,
//            RequestMethod.OPTIONS})
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductRequest request) {
        service.createProduct(request);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long productId) {
        try {
            return new ResponseEntity<>(service.getById(productId),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getAll() {
        try {
            return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?>update(@RequestBody ProductRequest request,@PathVariable Long productId ){
        try {
            return ResponseHandler.responseHandler("Product updated successfully",
                    HttpStatus.OK,service.update(request,productId));
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
@DeleteMapping("/delete/{productId}")
public void delete(@PathVariable Long productId){
       service.delete(productId);

}
@GetMapping("/getAllDetails/{id}")
    public ResponseEntity<ProductVariantInventoryResponse> getAllProductDetails(@PathVariable Long id){
        try {
            return new ResponseEntity<>(service.getAllProductDetails(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
}
@PutMapping("/updateAllDetails/{id}")
    public void updateAllDetails(@RequestBody ProductUpdateRequest request, @PathVariable Long id){
        service.updateAllDetails(request,id);


}
}
