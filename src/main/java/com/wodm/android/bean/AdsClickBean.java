package com.wodm.android.bean;

import com.google.gson.annotations.Expose;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by songchenyu on 16/12/8.
 */
@Table(name = "adsclickbean")
public class AdsClickBean implements Serializable {
    private String id;
    /**广告信息表id*/
    @Expose
    private Long adNum;

    /**点击量（该广告一天的点击量)*/
    @Expose
    private int clickCount=0;

    /**分享量（该广告一天的分享量）*/
    @Expose
    private Long shareCount=0l;

    /**通过分享进入应用人数*/
    @Expose
    private Long shareAppNum=0l;

    /**分享点击次数*/
    @Expose
    private Long shareClickNum=0l;

    /**当日收入*/
    @Expose
    private Double incomeDay=0.0;

    /**临时使用的时间字段 (时间传递yyyy-MM-dd HH:mm:ss 格式的字符串)*/
    @Expose
    private String times;

    public Long getAdNum() {
        return adNum;
    }

    public void setAdNum(Long adNum) {
        this.adNum = adNum;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public Long getShareAppNum() {
        return shareAppNum;
    }

    public void setShareAppNum(Long shareAppNum) {
        this.shareAppNum = shareAppNum;
    }

    public Long getShareClickNum() {
        return shareClickNum;
    }

    public void setShareClickNum(Long shareClickNum) {
        this.shareClickNum = shareClickNum;
    }

    public Double getIncomeDay() {
        return incomeDay;
    }

    public void setIncomeDay(Double incomeDay) {
        this.incomeDay = incomeDay;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
