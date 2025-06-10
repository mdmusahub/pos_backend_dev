package com.sm.backend.service;


import com.sm.backend.request.CategoryRequest;

public interface CategoryService {
    void createCategory(CategoryRequest request);


    Object getbyId(Long userId);

    void delete(Long categoryId);

    Object updateCategory(Long categoryId, CategoryRequest request);

    Object getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
