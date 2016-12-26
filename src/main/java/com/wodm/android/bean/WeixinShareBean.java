package com.wodm.android.bean;

/**
 * Created by songchenyu on 16/12/26.
 */

public class WeixinShareBean {
    private String targurl;
    private String title;
    private String description;
    private String imageUrl;
    private int scene;

    public String getTargurl() {
        return targurl;
    }

    public void setTargurl(String targurl) {
        this.targurl = targurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }
}
