package com.sm.backend.mappers;
import com.sm.backend.exceptionalHandling.GrandParentExistsException;
import com.sm.backend.exceptionalHandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sm.backend.model.Category;
import com.sm.backend.request.CategoryRequest;
import com.sm.backend.repository.CategoryRepository;
import com.sm.backend.response.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapperImpl {
    private final CategoryRepository repository;
    @Autowired
    public CategoryMapperImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    //    @Bean
//    public ModelMapper modelMapper(){
//        return new ModelMapper();
//    }
public Category convert(CategoryRequest request) {
    Category build = Category.builder()
            .description(request.getDescription())
            .name(request.getName())
            .build();
    if (request.getParentId() != null) {
        Category category1 = repository.findById(request.getParentId()).orElseThrow(()
                -> new ResourceNotFoundException("invalid category ID"));
        if (category1.getParentId() != null) {
            throw new GrandParentExistsException("can not create a category with grandparent");
        }
        build.setParentId(category1);
    }
    return build;

}
public List<CategoryResponse> categoryResponse (List<Category> category){
        List<CategoryResponse> list = new ArrayList<>();
    System.out.println("Category \n"+category);
        for(Category all : category) {
            CategoryResponse response = CategoryResponse.builder()
                    .categoryId(all.getId())
                    .name(all.getName())
                    .description(all.getDescription())
                    .parentId(all.getParentId())
                    .build();
            list.add(response);
        }
    System.out.println("list \n"+list);
        return list;
}

}
