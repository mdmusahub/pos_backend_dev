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
public class ReadById implements CategoryOperation{

    private final CategoryRepository repository;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_BY_ID;
    }

    @Override
    public CategoryResponse execute(CategoryRequest request) {
        Category category =
                repository.findById(request.getCategoryId()).orElseThrow(()
                -> new ResourceNotFoundException("invalid category Id" ));
//        CategoryResponse.builder()
//                .categoryId(category.getId())
        return new CategoryResponse(category);
    }
}
