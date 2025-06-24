package com.sm.backend.serviceImpl;

import com.sm.backend.exceptionalHandling.CategoryAlreadyExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createCategory(CategoryRequest request) {
        Optional<Category> byName = repository.findCategoryByName(request.getName());
        if (byName.isPresent()){
         throw new CategoryAlreadyExistsException("the Category " +request.getName()+" already exists");
        }
      else {
            Category category = new Category();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            if (request.getParentId()!=null) {
                Category category1 = repository.findById(request.getParentId()).orElseThrow(() -> new ResourceNotFoundException("invalid category ID"));
                category.setParentId(category1);
            }
            repository.save(category);
        }
    }

    @Override
    public CategoryResponse getById(Long categoryId) {
  Category category = repository.findById(categoryId)
 .orElseThrow(() -> new ResourceNotFoundException("invalid category Id" ));
return new CategoryResponse(category);
}

    @Override
    public void delete(Long categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("invalid category ID"));
        repository.delete(category);
    }

    @Override
    public Object updateCategory(Long categoryId, CategoryRequest request) {
        Category category = repository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("invalid Category ID"));
        if (request.getDescription()!=null){
            category.setDescription(request.getDescription());
        }
if (request.getName()!=null){
    category.setName(request.getName());
}

if (request.getParentId()!=null){
    Category parentId = repository.findById(request.getParentId()).orElseThrow(() -> new ResourceNotFoundException("invalid Category ID"));
    category.setParentId(parentId);
}
    return repository.save(category);
}

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> all = repository.findAll();
        return all.stream().map(CategoryResponse::new).toList();
    }



}
