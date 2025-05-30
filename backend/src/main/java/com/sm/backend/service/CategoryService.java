package com.sm.backend.service;


import com.sm.backend.model.Category;
import com.sm.backend.request.CategoryRequest;

public interface CategoryService {
    void register(CategoryRequest request);


    Object getbyId(Long userId);

    void delete(Long categoryId);

    Object updateCategory(Long categoryId, CategoryRequest request);

    Object findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
