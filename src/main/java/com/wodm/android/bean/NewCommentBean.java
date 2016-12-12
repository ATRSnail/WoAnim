package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 * 修后改之的动漫画详情页面的评论数据
 */
public class NewCommentBean implements Serializable {
    private boolean isZan = false;
    /**
     * sendId : 280
     * sendPortrait : http://59.108.94.55:8899/static//head/20161208/280/280142509.jpg
     * sendNickName : VIP之小将之家乡，亲人
     * gradeValue : 5
     * times : 2016-12-09 09:58:46
     * sendCommentId : 820
     * sendCommentContent : 5
     * goodCount : 1
     * isLike : 0
     * isFollow : 0
     * receiveId : null
     * receiveNickName : null
     * receiveCommentId : null
     * receiveCommentContent : null
     * resourceId : 144
     */

    private int sendId;
    private String sendPortrait;
    private String sendNickName;
    private int gradeValue;
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

    public boolean isZan() {
        return isZan;
    }

    public void setZan(boolean zan) {
        isZan = zan;
    }
   private boolean  guanzhu =false;

    public boolean isGuanzhu() {
        return guanzhu;
    }

    public void setGuanzhu(boolean guanzhu) {
        this.guanzhu = guanzhu;
    }

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
