package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 */
public class CommentBean implements Serializable{
    /**
     * 修改之后的动漫画详情页面的评论数据
     * sendId : 280
     * sendPortrait : http://59.108.94.55:8899/static//head/20161208/280/280142509.jpg
     * sendNickName : VIP之小将之家乡，亲人
     * gradeValue : 5
     * times : 2016-12-01 11:11:18
     * sendCommentId : 787
     * sendCommentContent : [em9][em9][em9]
     * goodCount : 0
     * isLike : 0
     * receiveId : 198
     * receiveNickName : 小康康
     * receiveCommentId : 789
     * receiveCommentContent : 小康测试回复你的
     */

//    private int sendId;
//    private String sendPortrait;
//    private String sendNickName;
//    private int gradeValue;
//    private String times;
//    private int sendCommentId;
//    private String sendCommentContent;
//    private int goodCount;
//    private int isLike;
//    private int receiveId;
//    private String receiveNickName;
//    private int receiveCommentId;
//    private String receiveCommentContent;
//
//    public int getSendId() {
//        return sendId;
//    }
//
//    public void setSendId(int sendId) {
//        this.sendId = sendId;
//    }
//
//    public String getSendPortrait() {
//        return sendPortrait;
//    }
//
//    public void setSendPortrait(String sendPortrait) {
//        this.sendPortrait = sendPortrait;
//    }
//
//    public String getSendNickName() {
//        return sendNickName;
//    }
//
//    public void setSendNickName(String sendNickName) {
//        this.sendNickName = sendNickName;
//    }
//
//    public int getGradeValue() {
//        return gradeValue;
//    }
//
//    public void setGradeValue(int gradeValue) {
//        this.gradeValue = gradeValue;
//    }
//
//    public String getTimes() {
//        return times;
//    }
//
//    public void setTimes(String times) {
//        this.times = times;
//    }
//
//    public int getSendCommentId() {
//        return sendCommentId;
//    }
//
//    public void setSendCommentId(int sendCommentId) {
//        this.sendCommentId = sendCommentId;
//    }
//
//    public String getSendCommentContent() {
//        return sendCommentContent;
//    }
//
//    public void setSendCommentContent(String sendCommentContent) {
//        this.sendCommentContent = sendCommentContent;
//    }
//
//    public int getGoodCount() {
//        return goodCount;
//    }
//
//    public void setGoodCount(int goodCount) {
//        this.goodCount = goodCount;
//    }
//
//    public int getIsLike() {
//        return isLike;
//    }
//
//    public void setIsLike(int isLike) {
//        this.isLike = isLike;
//    }
//
//    public int getReceiveId() {
//        return receiveId;
//    }
//
//    public void setReceiveId(int receiveId) {
//        this.receiveId = receiveId;
//    }
//
//    public String getReceiveNickName() {
//        return receiveNickName;
//    }
//
//    public void setReceiveNickName(String receiveNickName) {
//        this.receiveNickName = receiveNickName;
//    }
//
//    public int getReceiveCommentId() {
//        return receiveCommentId;
//    }
//
//    public void setReceiveCommentId(int receiveCommentId) {
//        this.receiveCommentId = receiveCommentId;
//    }
//
//    public String getReceiveCommentContent() {
//        return receiveCommentContent;
//    }
//
//    public void setReceiveCommentContent(String receiveCommentContent) {
//        this.receiveCommentContent = receiveCommentContent;
//    }


    private int id;
    private int sendId;
    private String sendPortrait;
    private String sendAccountName;
    private String content;
    private long createTime;
    private String createTimeStr;
    private int goodCount;
    private int commentCount;
    private boolean isZan = false;

    public boolean isZan() {
        return isZan;
    }

    public void setZan() {
        isZan = !isZan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSendAccountName() {
        return sendAccountName;
    }

    public void setSendAccountName(String sendAccountName) {
        this.sendAccountName = sendAccountName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

}
