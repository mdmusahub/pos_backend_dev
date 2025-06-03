package com.sm.backend.Service;

import com.sm.backend.Repository.CategoryRepository;
import com.sm.backend.Request.CategoryRequest;
import com.sm.backend.exceptionHandler.CategoryAlreadyExistsException;
import com.sm.backend.exceptionHandler.ResourceNotFoundException;
import com.sm.backend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository theCategoryRepository){
        categoryRepository = theCategoryRepository;
    }

    public Object findAll(Integer pageNumber, Integer pageSize){
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return categoryRepository.findAll(pageable);
    }

    public void addNew (CategoryRequest category) throws CategoryAlreadyExistsException{

        Optional<Category> name = categoryRepository.findByName(category.getName());
        if (name.isPresent()){
            throw new CategoryAlreadyExistsException("Already Exists");
        }else {

            Category cat = new Category();
            Category category1 = categoryRepository.findById(category.getParentId()).orElseThrow(()
                    -> new RuntimeException("Parent Id Not Found"));
            cat.setName(category.getName());
            cat.setDescription(category.getDescription());
            cat.setParentId(category1);
            categoryRepository.save(cat);
        }
    }
    public Category findById (Long id) throws ResourceNotFoundException{
//        return categoryRepository.findById(id).orElseThrow(()->
//                    new RuntimeException("Id Not Found"));
        Optional<Category> category = categoryRepository.findById(id);
        Category cat = null;
        if (category.isPresent()){
            cat = category.get();
        }else {
            throw new ResourceNotFoundException("Id Not Found");
        }
        return cat;
        }

    public ResponseEntity update (Long id, CategoryRequest category)
    throws CategoryAlreadyExistsException{
        if (categoryRepository.existsById(id)){
            Category cat = categoryRepository.findById(id).orElseThrow(()->
                    new RuntimeException("Id Not Found"));

            if (category.getName() != null ){
                cat.setName(category.getName());
            }
            if (category.getDescription() != null){
                cat.setDescription(category.getDescription());
            }
            if (category.getParentId() != null){
                Category category1=categoryRepository.findById(category.getParentId())
                        .orElseThrow(()->new RuntimeException("Category Id Not Found"));
                cat.setParentId(category1);
            }
            categoryRepository.save(cat);
            return ResponseEntity.status(200).body("Update Succesfull");
        }else{
//            return ResponseEntity.status(400).body("Id Not Found");
            throw new CategoryAlreadyExistsException("Id Not Found");
        }
    }

    public ResponseEntity deleteById (Long id) throws CategoryAlreadyExistsException{
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return ResponseEntity.status(200).body("Delete Succsefull");
        }else{
//            return ResponseEntity.status(400).body("Id Not Found");
            throw new CategoryAlreadyExistsException("Id Not Found");
        }
    }
}
