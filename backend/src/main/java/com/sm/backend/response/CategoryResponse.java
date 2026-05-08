package com.sm.backend.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sm.backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private  String description;
    private Category parentId;
    private String message;
    private List<CategoryResponse> categories;

    public CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
   this.parentId = category.getParentId();
    }
}




