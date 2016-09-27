package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by moon1 on 2016/4/28.
 */
public class OpusBean implements Serializable {
    private String id;//作品 id
    private String name;//作品名称
    private String categoryId;//分类 id
    private String copyrightId;//版权 id
    private String type;//作品类型
    private String areaId;//区域 id
    private String author;//作者
    private String years;//年份
    private String showImage;//首页图片
    private String desp;//作品简介
    private String score;//作品评分
    private String chapter;//作品章数/集数
    private String playCount;//播放次数
    private String commentCount;//评论数
    private String goodCount;//点赞数
    private String collectionCount;//收藏数
    private String nowChapter;//当前章节/集数
    private String nowChapterId;//当前章节/集数的 id
    private String title;//作品标题

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(String copyrightId) {
        this.copyrightId = copyrightId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public String getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getNowChapter() {
        return nowChapter;
    }

    public void setNowChapter(String nowChapter) {
        this.nowChapter = nowChapter;
    }

    public String getNowChapterId() {
        return nowChapterId;
    }

    public void setNowChapterId(String nowChapterId) {
        this.nowChapterId = nowChapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
