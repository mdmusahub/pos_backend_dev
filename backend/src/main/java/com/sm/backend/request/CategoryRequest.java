package com.sm.backend.request;

import lombok.Data;


@Data
public class CategoryRequest {
private String name;
 private  String description;
 private Long parentId;
}
