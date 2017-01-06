package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 */
public class ChapterBean implements Serializable {
    /**
     * id : 7738
     * resourceId : 549
     * showImage : null
     * desp : null
     * title : 1
     * contentUrl : http://202.106.63.82/static/ftproot/ltcp03/d/zhenghexiaxiyang/1.mp4?sign=URWl4F__tpPtjk6n7rWh5w&t=1483691388
     * playCount : 0
     * chapter : 1
     * isWatch : 0
     */

    private String id;
    private int resourceId;
    private String showImage;
    private String desp;
    private String title;
    private String contentUrl;
    private int playCount;
    private int chapter;
    private int isWatch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowImage() {
        return showImage;
    }

    public String getDesp() {
        return desp;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private int isCheck = 0; //0正常状态 1 正在下载或者等待下载 2 下载完成 3选中状态

    public int IsCheck() {
        return isCheck;
    }
    public void setCheck(int check) {
        isCheck = check;
    }




    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getIsWatch() {
        return isWatch;
    }

    public void setIsWatch(int isWatch) {
        this.isWatch = isWatch;
    }
}
