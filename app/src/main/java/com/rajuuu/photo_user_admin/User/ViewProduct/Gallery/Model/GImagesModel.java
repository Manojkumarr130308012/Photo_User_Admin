package com.rajuuu.photo_user_admin.User.ViewProduct.Gallery.Model;

public class GImagesModel {

    private String image_id;
    private String image_src;

    public GImagesModel(String image_id, String image_src) {
        this.image_id = image_id;
        this.image_src = image_src;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
