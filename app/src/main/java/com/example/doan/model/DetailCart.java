package com.example.doan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailCart implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("idGH")
    @Expose
    private int idGH;
    @SerializedName("idSP")
    @Expose
    private int idSP;
    @SerializedName("ha")
    @Expose
    private String ha;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("sl")
    @Expose
    private int soluong;
    @SerializedName("gia")
    @Expose
    private String gia;

    public DetailCart(int id, int idGH, int idSP, int soluong, String gia) {
        this.id = id;
        this.idGH = idGH;
        this.idSP = idSP;
        this.soluong = soluong;
        this.gia = gia;
    }

    public DetailCart(int idGH, int idSP, String ten, String ha, int soluong, String gia) {
        this.idGH = idGH;
        this.idSP = idSP;
        this.ha = ha;
        this.ten = ten;
        this.soluong = soluong;
        this.gia = gia;
    }

    public DetailCart(int id, int idGH, int idSP, String ha, String ten, int soluong, String gia) {
        this.id = id;
        this.idGH = idGH;
        this.idSP = idSP;
        this.ha = ha;
        this.ten = ten;
        this.soluong = soluong;
        this.gia = gia;
    }

    public String getHa() {
        return ha;
    }

    public void setHa(String ha) {
        this.ha = ha;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGH() {
        return idGH;
    }

    public void setIdGH(int idGH) {
        this.idGH = idGH;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    protected DetailCart(Parcel in) {
        id = in.readInt();
        idGH = in.readInt();
        idSP = in.readInt();
        ten = in.readString();
        ha = in.readString();
        soluong = in.readInt();
        gia = in.readString();
    }

    public static final Creator<DetailCart> CREATOR = new Creator<DetailCart>() {
        @Override
        public DetailCart createFromParcel(Parcel in) {
            return new DetailCart(in);
        }

        @Override
        public DetailCart[] newArray(int size) {
            return new DetailCart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idGH);
        dest.writeInt(idSP);
        dest.writeString(ten);
        dest.writeString(ha);
        dest.writeInt(soluong);
        dest.writeString(gia);
    }
}
