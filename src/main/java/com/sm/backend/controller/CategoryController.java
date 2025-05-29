package com.sm.backend.controller;

import com.sm.backend.model.Category;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public void addnew(@RequestBody CategoryRequest category){
        categoryService.addNew(category);
    }


    @GetMapping
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id){
        return categoryService.findById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id , @RequestBody CategoryRequest thecategory){
        return categoryService.update(id,thecategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return categoryService.deletById(id);
    }
}
