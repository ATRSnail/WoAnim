package com.wodm.android.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by json on 2016/4/27.
 */
public class ObjectBean implements Serializable {

    private int id;
    private String name;
    private int categoryId;
    private int copyrightId;
    private int type;
    private int areaId;
    private String author;
    private String years;
    private String showImage;
    private String desp;
    private float score;

    private int playCount;
    private int commentCount;
    private int goodCount;
    private int collectionCount;

    private int resourceType;

    private int chapter;
    private int nowChapter;
    private int nowChapterId;
    private String title;
    private String briefDesp;
    private int isCollect;
    private String image_296_296;

    public String getImage_296_296() {
        return image_296_296;
    }

    public void setImage_296_296(String image_296_296) {
        this.image_296_296 = image_296_296;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(int copyrightId) {
        this.copyrightId = copyrightId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
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
        if (TextUtils.isEmpty(showImage)) {
            return "";
        }
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getNowChapter() {
        return nowChapter;
    }

    public void setNowChapter(int nowChapter) {
        this.nowChapter = nowChapter;
    }

    public int getNowChapterId() {
        return nowChapterId;
    }

    public void setNowChapterId(int nowChapterId) {
        this.nowChapterId = nowChapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefDesp() {
        return briefDesp;
    }

    public void setBriefDesp(String briefDesp) {
        this.briefDesp = briefDesp;
    }
}
