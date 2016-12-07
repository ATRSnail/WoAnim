package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by songchenyu on 16/9/28.
 */

public class BarrageBean implements Serializable {
    public int id;
    public int resourceId;
    public int chapterId;
    public int sendId;
    public long createTime;
    public String content;
    public String color;
    public int  location;


    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BarrageBean{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", chapterId=" + chapterId +
                ", sendId=" + sendId +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
