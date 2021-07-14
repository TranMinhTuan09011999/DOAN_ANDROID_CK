package com.example.doan.model;

public class ImageDetails {
    private Long id;
    private Integer imageid;
    private String image;

    public ImageDetails(Long id, Integer imageid, String image) {
        this.id = id;
        this.imageid = imageid;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
