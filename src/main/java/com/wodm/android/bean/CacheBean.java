package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/5/16.
 */
public class CacheBean implements Serializable {

    private int id;
    private String resourceId;
    private String url;//下载地址
    private String logo;//封面地址
    private String title; //下载作品名称
    private int sumChapter; //已经缓存完成数量
    private int chapters; //已经缓存完成数量
    private int resourceType; //作品类型 1：动画 2：漫画
    private String path;//缓存路径
//    private int quality;//清晰度 0:原画质 1：超清 2：高清 3:标清 4:流畅 等等
//
//    public int getQuality() {
//        return quality;
//    }
//
//    public void setQuality(int quality) {
//        this.quality = quality;
//    }


    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getId() {
    return id;
}

    public void setId(int id) {
        this.id = id;
    }

    public void setSumChapter(int sumChapter) {
        this.sumChapter = sumChapter;
    }

    public int getSumChapter() {
        return sumChapter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
