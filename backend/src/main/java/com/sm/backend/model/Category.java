package com.sm.backend.model;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String description;

    private Category parentId;

}
