package com.sm.backend.controller;


import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService service;
@Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

//    @PostMapping(value = "/create",consumes = "multipart/form-data")
    @PostMapping("/create")
public void createCategory(@RequestBody CategoryRequest request){
    service.createCategory(request);
}
@GetMapping("/getById/{categoryId}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long categoryId){
    try {
        return new ResponseEntity<>(service.getById(categoryId),HttpStatus.OK);
    }
catch (Exception e){
        throw new ResourceNotFoundException("invalid category ID");
}
}
@DeleteMapping("/delete/{categoryId}")
    public void delete(@PathVariable Long categoryId){
    service.delete(categoryId);
}
@PutMapping("/update/{categoryId}")
    public ResponseEntity<?>updateCategory(@RequestBody CategoryRequest request ,@PathVariable Long categoryId){
    try {
        return ResponseHandler.responseHandler("updated",HttpStatus.OK,service.updateCategory(categoryId,request));
    }
catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}
}

@GetMapping("/getAll")
public ResponseEntity<List<CategoryResponse>> getAll(){
    try {
        return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
    }
catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}


}



}
