package com.sm.backend.controller;


import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.responseHandler.ResponseHandler;
import com.sm.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService service;
@Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/register")
public void register(@RequestBody CategoryRequest request){
    service.register(request);
}
@GetMapping("/getById/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable Long categoryId){
    try {
        return ResponseHandler.responseHandler("id found Successfully", HttpStatus.OK,service.getbyId(categoryId));
    }
catch (Exception e){
        throw new ResourceNotFoundException("invalid category ID");
}
}
@DeleteMapping("/delete{categoryId}")
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
public ResponseEntity<?>findAll(
        @RequestParam(required = false,defaultValue = "0")Integer pageNumber,
        @RequestParam(required = false,defaultValue = "10")Integer pageSize,
        @RequestParam(required = false,defaultValue = "name")String sortBy,
        @RequestParam(required = false,defaultValue = "asc")String sortDir
){
    try {
        return ResponseHandler.responseHandler("List of categories",HttpStatus.OK,service.findAll(pageNumber,pageSize,sortBy,sortDir));
    }
catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}


}



}
