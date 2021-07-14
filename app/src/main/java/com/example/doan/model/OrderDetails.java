package com.example.doan.model;

public class OrderDetails {
    private Order order;

    private SanPham1 product;

    private Long quantity;

    private Double amount;

    private Integer discount;

    public OrderDetails(Order order, SanPham1 product, Long quantity, Double amount, Integer discount) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
        this.discount = discount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
