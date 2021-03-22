package com.rajuuu.photo_user_admin.User.Home;

public class BannerModel {
    private String id;
    private String img;

    public BannerModel(String id, String img) {
        this.id = id;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
