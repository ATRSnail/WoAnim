package com.wodm.android.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by json on 2016/5/5.
 */
public class SeacherBean {
    private int id;
    @Expose
    private String content;
    @Expose
    private long createTime = System.currentTimeMillis();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
