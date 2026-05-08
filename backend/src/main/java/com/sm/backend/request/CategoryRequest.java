package com.sm.backend.request;

import com.sm.backend.util.RequestType;
import lombok.Data;


@Data
public class CategoryRequest {
private String name;
 private  String description;
 private Long parentId;
 private Long categoryId;
 private RequestType requestType;
}
