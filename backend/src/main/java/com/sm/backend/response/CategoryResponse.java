package com.sm.backend.response;


import com.sm.backend.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String name;
    private  String discription;
    private Long parentId;
    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.discription = category.getDiscription();
   this.parentId = category.getCategoryId();
    }
}
