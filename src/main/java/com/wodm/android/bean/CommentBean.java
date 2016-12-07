package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 */
public class CommentBean implements Serializable{
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
