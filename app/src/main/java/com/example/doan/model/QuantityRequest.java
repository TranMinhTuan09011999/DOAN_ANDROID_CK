package com.example.doan.model;

public class QuantityRequest {
    private Long quantity;

    public QuantityRequest(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
