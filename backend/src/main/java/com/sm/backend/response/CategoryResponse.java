package com.sm.backend.response;


import com.sm.backend.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private  String description;
    private Category parentId;

    public CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
   this.parentId = category.getParentId();
    }
}
