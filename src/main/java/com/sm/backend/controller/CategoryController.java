package com.sm.backend.controller;

import com.sm.backend.Request.CategoryRequest;
import com.sm.backend.Service.CategoryService;
import com.sm.backend.exceptionHandler.CategoryAlreadyExistsException;
import com.sm.backend.exceptionHandler.ResourceNotFoundException;
import com.sm.backend.responseHandler.ResponseHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Object finddAll(@RequestParam(required = false, defaultValue = "0")Integer pageNumber,
                           @RequestParam(required = false, defaultValue = "10")Integer pageSize)
    throws ResourceNotFoundException {
        try{
            return ResponseHandle.responseHandle("Show Data", HttpStatus.OK,
                    categoryService.findAll(pageNumber, pageSize));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+ e.getMessage());
        }
    }
    @PostMapping
    public void save (@RequestBody CategoryRequest category) throws CategoryAlreadyExistsException {
        try {
         categoryService.addNew(category);
        }catch (Exception e){
            throw new CategoryAlreadyExistsException("Error : "+e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) throws ResourceNotFoundException{
        try {
        return ResponseHandle.responseHandle("Show Id",HttpStatus.OK,
                categoryService.findById(id));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable Long id,
                                  @RequestBody CategoryRequest categoryRequest)
    throws Exception{
//        Category category = categoryService.findById(id);
//        if (category != null){
//            return ResponseEntity.status(400).body("Id Not Found");
//        }else {
            try {
                return ResponseHandle.responseHandle("Update Sucessfull", HttpStatus.OK,
                        categoryService.update(id, categoryRequest));
            } catch (Exception e) {
                throw new CategoryAlreadyExistsException("Error : " + e.getMessage());
            }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable Long id)throws Exception{
        try {
        return ResponseHandle.responseHandle("Delete Id",HttpStatus.OK,
                categoryService.deleteById(id));
        }catch (Exception e){
            throw new ResourceNotFoundException("Error : "+e.getMessage());
        }
    }
}
