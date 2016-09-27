package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/5/16.
 */
public class DownBean implements Serializable {
    private int id;
    private String chapterId;//章节id
    private String url;//下载地址
    private String logo;//下载地址
    private String title; //下载作品名称
    private String chapter; //当前作品集 第几集
    private long maxSize; //作品大小 单位字节
    private long progress; //下载进度
    private int resourceType; //作品类型 1：动画 2：漫画
    private DowmStatus status; //下载状态
    private String path;//缓存路径
    private int quality;//清晰度 0:原画质 1：超清 2：高清 3:标清 4:流畅 等等

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public DownBean() {
    }

    public DownBean(String path, String url, String logo, String title, String chapter, long maxSize, long progress, DowmStatus status) {
        this.path = path;
        this.url = url;
        this.logo = logo;
        this.title = title;
        this.chapter = chapter;
        this.maxSize = maxSize;
        this.progress = progress;
        this.status = status;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public void setStatus(DowmStatus status) {
        this.status = status;
    }

    public DowmStatus getStatus() {
        return status;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public int getResourceType() {
        return resourceType;
    }
}
