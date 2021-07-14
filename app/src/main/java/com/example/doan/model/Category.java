package com.example.doan.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("products")
    @Expose
    private SanPham1[] products;

    public Category(Long id, String categoryName, SanPham1[] products) {
        this.id = id;
        this.categoryName = categoryName;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public SanPham1[] getProducts() {
        return products;
    }

    public void setProducts(SanPham1[] products) {
        this.products = products;
    }
}
