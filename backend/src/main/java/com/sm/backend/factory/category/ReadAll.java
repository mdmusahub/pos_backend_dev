package com.sm.backend.factory.category;

import com.sm.backend.mapperImpl.CategoryMapperImpl;
import com.sm.backend.model.Category;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.response.CategoryResponse;
import com.sm.backend.util.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAll implements CategoryOperation{

    private final CategoryRepository repository;

    private final CategoryMapperImpl categoryMapper;

    @Override
    public RequestType getRequestType() {
        return RequestType.READ_ALL;
    }

    @Override
    public CategoryResponse execute(CategoryRequest request) {
        List<Category> all = repository.findAll();
//        return all.stream().map(CategoryResponse::new).toList();

        return CategoryResponse.builder()
                .categories(categoryMapper.categoryResponse(all))
                .build();
    }
}
