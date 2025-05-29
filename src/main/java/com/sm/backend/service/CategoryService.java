package com.sm.backend.service;

import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void addNew(CategoryRequest category) {
            Category category1 = new Category();
            Category category2 = categoryRepository.findById(category.getParentId()).orElseThrow(()->
                    new RuntimeException("Id not found"));
            category1.setName(category.getName());
            category1.setDescription(category.getDescription());
            category1.setParentId(category2);
            categoryRepository.save(category1);

    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
                new RuntimeException("id not found"));

    }

    public ResponseEntity update (Long id,CategoryRequest category){
        if (categoryRepository.existsById(id)){
            Category category1 = categoryRepository.findById(id).orElseThrow(()->
                    new RuntimeException("id not found"));
            if (category.getName() != null){
                category1.setName(category.getName());

            }
            if (category.getDescription() != null){
                category1.setDescription(category.getDescription());
            }

            if (category.getParentId() != null){
                Category category2=categoryRepository.findById(category.getParentId()).orElseThrow(()->
                        new RuntimeException("Id Not Found"));
                category1.setParentId(category2);
            }
             categoryRepository.save(category1);
            return ResponseEntity.status(200).body("Update successfuly");
        }else {

            return ResponseEntity.status(200).body("Id not found");
        }


    }

    public ResponseEntity deletById (Long id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return ResponseEntity.status(200).body("delete successfuly");
        }else {
            return ResponseEntity.status(200).body("Id not found");
        }
    }

}