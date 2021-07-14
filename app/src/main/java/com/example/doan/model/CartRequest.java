package com.example.doan.model;

public class CartRequest {
    private Long productId;
    private Long userId;
    private Integer qty;
    private double price;

    public CartRequest(Long productId, Long userId, Integer qty, double price) {
        this.productId = productId;
        this.userId = userId;
        this.qty = qty;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }
}
