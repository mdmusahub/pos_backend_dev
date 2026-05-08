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
public class UpdateCategory implements CategoryOperation{

    private final CategoryRepository repository;

    @Override
    public RequestType getRequestType() {
        return RequestType.UPDATE;
    }

    @Override
    public CategoryResponse execute(CategoryRequest request) {
        Category category = repository.findById(
                request.getCategoryId()).orElseThrow(()
                -> new ResourceNotFoundException("invalid Category ID"));
        if (request.getDescription()!=null){
            category.setDescription(request.getDescription());
        }
        if (request.getName()!=null){
            category.setName(request.getName());
        }

        if (request.getParentId()!=null){
            Category parentId = repository.findById(
                    request.getParentId()).orElseThrow(()
                    -> new ResourceNotFoundException("invalid Category ID"));
            category.setParentId(parentId);
        }
        repository.save(category);
        return CategoryResponse.builder()
                .message("Updated")
                .build();
    }
}
