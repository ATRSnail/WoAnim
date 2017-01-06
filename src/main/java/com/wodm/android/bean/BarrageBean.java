package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by songchenyu on 16/9/28.
 */

public class BarrageBean implements Serializable {
    /**动漫/动画的作品的id*/
    private Long resourceId=0l;

    /**动漫/漫画的章节id*/
    private Long chapterId=0l;

    /** 发表弹幕用户的id */
    private Long sendId = 0l;

    /**弹幕的内容*/
    private String content;

    /**播放时间*/
    private String playTime;


    /**1表示小文字,2表示的是中文字 3:表示的是打文字*/
    //private int size=0;

    /**颜色*/
    private String color;

    /**位置 1:表示上 2: 表示中 3 表示下*/
    private int location;

    /* 是否已经展示弹幕,防止出现重复 */
    private boolean isShowNm;

    public boolean isShowNm() {
        return isShowNm;
    }

    public void setShowNm(boolean showNm) {
        isShowNm = showNm;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
