package com.sm.backend.response;

import com.sm.backend.model.Category;

public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private CategoryResponse parentId;



    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.parentId = new CategoryResponse(category.getParentId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryResponse getParentId() {
        return parentId;
    }

    public void setParentId(CategoryResponse parentId) {
        this.parentId = parentId;
    }
}
