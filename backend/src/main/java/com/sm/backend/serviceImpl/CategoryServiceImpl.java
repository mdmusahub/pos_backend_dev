package com.sm.backend.serviceImpl;

import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(CategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setDiscription(request.getDiscription());
        if (request.getParentId()!=null) {
            Category category1 = repository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("id not found"));
            category.setParentId(category1);
        }
        repository.save(category);
    }

    @Override
    public Object getbyId(Long categoryId) {
  Category category = repository.findById(categoryId)
 .orElseThrow(() -> new RuntimeException("id not found"));
return new CategoryResponse(category);
}

    @Override
    public void delete(Long categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() -> new RuntimeException("error"));
        repository.delete(category);
    }

    @Override
    public Object updateCategory(Long categoryId, CategoryRequest request) {
        Category category = new Category();
        if (request.getDiscription()!=null){
            category.setDiscription(request.getDiscription());
        }
if (request.getName()!=null){
    category.setName(request.getName());
}
    return repository.save(category);
}

    @Override
    public Object findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")){
            sort =Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        return repository.findAll(pageable);
    }


}
