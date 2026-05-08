package com.sm.backend.factory.category;

import com.sm.backend.exceptionalHandling.CategoryAlreadyExistsException;
import com.sm.backend.exceptionalHandling.GrandParentExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import com.sm.backend.mapperImpl.CategoryMapperImpl;
import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCategory implements CategoryOperation {

    private final CategoryRepository repository;

    private final CategoryMapperImpl categoryMapper;

    @Override
    public RequestType getRequestType() {
        return RequestType.CREATE;
    }

    @Override
    public CategoryResponse execute(CategoryRequest request) {

        Optional<Category> byName = repository.findCategoryByName(request.getName());
        if (byName.isPresent()) {
            throw new CategoryAlreadyExistsException("the Category " + request.getName() + " already exists");
        } else {
//                Category category = this.modelMapper.map(request,Category.class);
            categoryMapper.convert(request);
            Category category = new Category();
            category.setName(request.getName());
            category.setDescription(request.getDescription());

            if (request.getParentId() != null) {
                Category category1 = repository.findById(request.getParentId()).orElseThrow(() -> new ResourceNotFoundException("invalid category ID"));
                if (category1.getParentId() != null) {
                    throw new GrandParentExistsException("can not create a category with grandparent");
                }
                category.setParentId(category1);
                repository.save(category);
            } else {
                repository.save(category);
            }
            return CategoryResponse.builder()
                    .message("Category created successfully")
                    .build();
        }
    }
}
