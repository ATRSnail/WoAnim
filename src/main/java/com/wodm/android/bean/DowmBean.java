package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/5/16.
 */
public class DowmBean implements Serializable {
    private int id;
    private String resourceId;//资源id
    private String chapterId;//章节id
    private String url;//下载地址
    private String logo;//下载地址
    private String title; //下载作品名称
    private String chapter; //当前作品集 第几集
    private long maxSize; //作品大小 单位字节
    private long progress; //下载进度
    private int resourceType; //作品类型 1：动画 2：漫画
    private String status; //下载状态
    private String path;//缓存路径
    private int quality;//清晰度 0:原画质 1：超清 2：高清 3:标清 4:流畅 等等

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
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

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setStatus(DowmStatus status) {
        this.status = status.name();
    }
}