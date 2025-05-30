package com.sm.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
private Long categoryId;
    private String name;
    private  String description;

@ManyToOne
@JoinColumn(name = "parent_id")
  private Category parentId;

}
