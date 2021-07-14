package com.example.doan.model;

import java.util.Date;

public class OrderRequest {
    private Long id;
    private Date order_date;
    private Double amount;
    private String receiver;
    private String address;
    private String phone_number;
    private Long user_id;
    private Integer status;

    public OrderRequest(Date order_date, Double amount, String receiver, String address, String phone_number, Long user_id, Integer status) {
        this.order_date = order_date;
        this.amount = amount;
        this.receiver = receiver;
        this.address = address;
        this.phone_number = phone_number;
        this.user_id = user_id;
        this.status = status;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
