package com.wodm.android.bean;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by songchenyu on 16/11/25.
 */
@Table(name = "userbehavier")
public class UserBehavierInfo implements Serializable {
    private String id;
    @Expose
    private int behavier_id;
    @Expose
    private long start_time;
    @Expose
    private long end_time;
    @Expose
    private long resourceId;
    @Expose
    private int clickNum;

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    private long totalTime;

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBehavier_id() {
        return behavier_id;
    }

    public void setBehavier_id(int behavier_id) {
        this.behavier_id = behavier_id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
}
