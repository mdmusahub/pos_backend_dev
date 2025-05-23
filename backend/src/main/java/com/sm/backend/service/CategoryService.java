package com.sm.backend.service;


import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity save(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());



        category.setDescription(request.getDescription());
        if(request.getParentId()!=null) {
            category.setParentId(categoryRepository.findById(request.getParentId()).get());
        }

        categoryRepository.save(category);
        return ResponseEntity.ok().body("category created");
    }

    public Category getCategory(Long id) {
        try{
            return categoryRepository.findById(id).orElseThrow(() ->new RuntimeException("id not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
    public List<CategoryResponse> getAllCategory() {
        List<CategoryResponse> responseList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList) {
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            response.setDescription(category.getDescription());
            if (category.getParentId()!=null){
                response.setParentId(category.getParentId());
            }
            responseList.add(response);
        }
        return responseList;}

    public ResponseEntity delete(Long id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }
        return ResponseEntity.ok().body("Category Deleted");
    }

    public ResponseEntity update(Long id, CategoryRequest request){
        if (categoryRepository.existsById(id)){
            Category category=categoryRepository.findById(id).get();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            category.setParentId(categoryRepository.findById(request.getParentId()).get());
            categoryRepository.save(category);
        }
        return ResponseEntity.ok().body("Category updated");
    }
}