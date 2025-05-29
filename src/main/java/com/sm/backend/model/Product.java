package com.sm.backend.model;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "name",nullable = false,length = 255)
    private String name;

    @Column(name = "sku",nullable = false,length = 255)
    private String sku;



    @Column(name = "price",nullable = false,length = 255)
    private double price;

    @Column(name = "description",nullable = false,length = 255)
    private String description;


    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @ManyToOne
    private Category categoryId;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Category getCategoryId() {
        return categoryId;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
