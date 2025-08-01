package com.sm.backend.service;


import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequest request);


  CategoryResponse getById(Long categoryId);

    void delete(Long categoryId);

    Object updateCategory(Long categoryId, CategoryRequest request);

    List<CategoryResponse> getAll();


}
