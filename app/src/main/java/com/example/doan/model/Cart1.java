package com.example.doan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Cart1 {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product")
    @Expose
    SanPham1 product;
    @SerializedName("quantity")
    @Expose
    private Long quantity;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("user_id")
    @Expose
    private Long user_id;

    public Cart1(Long id, SanPham1 product, Long quantity, double price, Long user_id) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SanPham1 getProduct() {
        return product;
    }

    public void setProduct(SanPham1 product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}

