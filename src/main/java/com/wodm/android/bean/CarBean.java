package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/5/4.
 */
public class CarBean implements Serializable {
    private int id;
    private int chapterId;
    private String contentUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
