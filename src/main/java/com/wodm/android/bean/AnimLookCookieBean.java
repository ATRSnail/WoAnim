package com.wodm.android.bean;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by songchenyu on 16/11/3.
 */
@Table(name = "animlookcookie")
public class AnimLookCookieBean {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Expose
    public String rescoureid;
    @Expose
    public String animUrl;
    @Expose
    public int lookTime;
    @Expose
    public String animname;
    @Expose
    public int totalTime;
    /**
     * 1 表示播放完成
     */
    @Expose
    public int playState;
    public String getRescoureid() {
        return rescoureid;
    }

    public void setRescoureid(String rescoureid) {
        this.rescoureid = rescoureid;
    }

    public String getAnimUrl() {
        return animUrl;
    }

    public void setAnimUrl(String animUrl) {
        this.animUrl = animUrl;
    }

    public int getLookTime() {
        return lookTime;
    }

    public void setLookTime(int lookTime) {
        this.lookTime = lookTime;
    }

    public String getAnimname() {
        return animname;
    }

    public void setAnimname(String animname) {
        this.animname = animname;
    }


    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getPlayState() {
        return playState;
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }
}
