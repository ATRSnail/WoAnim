package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 * 修后改之的动漫画详情页面的评论数据
 */
public class NewCommentBean implements Serializable {

    /**
     * sendId : 230
     * sendPortrait : http://202.106.63.98:8990/static//head/20161220/230/230860788.png
     * sendNickName : 这是我的手里剑大招
     * gradeValue : 4
     * gradeName : 萌新IV
     * times : 2016-12-16 11:18:46
     * sendCommentId : 986
     * sendCommentContent : 111
     * goodCount : 0
     * isLike : 0
     * isFollow : 0
     * receiveId : null
     * receiveNickName : null
     * receiveCommentId : null
     * receiveCommentContent : null
     * resourceId : 554
     */

    private int sendId;
    private String sendPortrait;
    private String sendNickName;
    private int gradeValue;
    private String gradeName;
    private String times;
    private int sendCommentId;
    private String sendCommentContent;
    private int goodCount;
    private int isLike;
    private int isFollow;
    private Object receiveId;
    private Object receiveNickName;
    private Object receiveCommentId;
    private Object receiveCommentContent;
    private int resourceId;

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendPortrait() {
        return sendPortrait;
    }

    public void setSendPortrait(String sendPortrait) {
        this.sendPortrait = sendPortrait;
    }

    public String getSendNickName() {
        return sendNickName;
    }

    public void setSendNickName(String sendNickName) {
        this.sendNickName = sendNickName;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getSendCommentId() {
        return sendCommentId;
    }

    public void setSendCommentId(int sendCommentId) {
        this.sendCommentId = sendCommentId;
    }

    public String getSendCommentContent() {
        return sendCommentContent;
    }

    public void setSendCommentContent(String sendCommentContent) {
        this.sendCommentContent = sendCommentContent;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public Object getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Object receiveId) {
        this.receiveId = receiveId;
    }

    public Object getReceiveNickName() {
        return receiveNickName;
    }

    public void setReceiveNickName(Object receiveNickName) {
        this.receiveNickName = receiveNickName;
    }

    public Object getReceiveCommentId() {
        return receiveCommentId;
    }

    public void setReceiveCommentId(Object receiveCommentId) {
        this.receiveCommentId = receiveCommentId;
    }

    public Object getReceiveCommentContent() {
        return receiveCommentContent;
    }

    public void setReceiveCommentContent(Object receiveCommentContent) {
        this.receiveCommentContent = receiveCommentContent;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
