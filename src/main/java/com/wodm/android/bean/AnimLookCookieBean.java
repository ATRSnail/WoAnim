package com.wodm.android.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by songchenyu on 16/11/3.
 */

public class AnimLookCookieBean {
    public void setId(int id) {
        this.id = id;
    }
    private int id;
    @Expose
    public String rescoureid;
    @Expose
    public String animUrl;
    @Expose
    public String lookTime;
    @Expose
    public String animname;
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

    public String getLookTime() {
        return lookTime;
    }

    public void setLookTime(String lookTime) {
        this.lookTime = lookTime;
    }

    public String getAnimname() {
        return animname;
    }

    public void setAnimname(String animname) {
        this.animname = animname;
    }


}
