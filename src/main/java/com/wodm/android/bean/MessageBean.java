package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by moon1 on 2016/5/11.
 */
public class MessageBean implements Serializable {

    private String content;
    private long createTime;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
