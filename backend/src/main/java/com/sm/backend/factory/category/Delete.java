package com.sm.backend.factory.category;

import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Delete implements CategoryOperation{

    private final CategoryRepository repository;

    @Override
    public RequestType getRequestType() {
        return RequestType.DELETE;
    }

    @Override
    public CategoryResponse execute(CategoryRequest request) {
        Category category = repository.findById(
                request.getCategoryId()).orElseThrow(()
                -> new ResourceNotFoundException("invalid category ID"));
        repository.delete(category);
        return CategoryResponse.builder()
                .message("deleted")
                .build();
    }
}
