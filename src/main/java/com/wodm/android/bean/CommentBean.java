package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 */
public class CommentBean implements Serializable {

    //    private int id;
//    private int sendId;
//    private String sendPortrait;
//    private String sendAccountName;
//    private String content;
//    private long createTime;
//    private String createTimeStr;
//    private int goodCount;
//    private int commentCount;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
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
//    public String getSendAccountName() {
//        return sendAccountName;
//    }
//
//    public void setSendAccountName(String sendAccountName) {
//        this.sendAccountName = sendAccountName;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public long getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(long createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getCreateTimeStr() {
//        return createTimeStr;
//    }
//
//    public void setCreateTimeStr(String createTimeStr) {
//        this.createTimeStr = createTimeStr;
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
//    public int getCommentCount() {
//        return commentCount;
//    }
//
//    public void setCommentCount(int commentCount) {
//        this.commentCount = commentCount;
//    }


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


    private boolean guanzhu = false;

    public boolean isZan() {
        return isLike == 1;
    }

    public void setZan() {
        this.isLike = isLike == 0 ? 1 : 0;
    }

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
