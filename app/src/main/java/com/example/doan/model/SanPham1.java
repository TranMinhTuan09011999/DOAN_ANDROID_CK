package com.example.doan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham1 implements Serializable,Parcelable {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Float price;
    @SerializedName("promotion")
    @Expose
    private Integer promotion;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("deletestatus")
    @Expose
    private int deletestatus;
    @SerializedName("quantity")
    @Expose
    private Long quantity;

    public SanPham1(Long id, String name, Float price, Integer promotion, String description, String image, int deletestatus, Long quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.description = description;
        this.image = image;
        this.deletestatus = deletestatus;
        this.quantity = quantity;
    }

    protected SanPham1(Parcel in) {
        id = in.readLong();
        name = in.readString();
        price = in.readFloat();
        promotion = in.readInt();
        description = in.readString();
        image = in.readString();
        deletestatus = in.readInt();
        quantity = in.readLong();
    }

    public static final Creator<SanPham1> CREATOR = new Creator<SanPham1>() {
        @Override
        public SanPham1 createFromParcel(Parcel in) {
            return new SanPham1(in);
        }

        @Override
        public SanPham1[] newArray(int size) {
            return new SanPham1[size];
        }
    };

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(int deletestatus) {
        this.deletestatus = deletestatus;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeInt(promotion);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeInt(deletestatus);
        dest.writeLong(quantity);
    }
}
