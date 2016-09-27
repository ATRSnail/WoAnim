package com.wodm.android.bean;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by json on 2016/4/29.
 */
public class ChapterBean implements Serializable {


    private String id;
    private int resourceId;
    private int type;
    private String showImage;
    private String desp;
    private String title;
    private String contentUrl;
    private int playCount;
    private int chapter;
    private int isCheck = 0; //0正常状态 1 正在下载或者等待下载 2 下载完成 3选中状态

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int IsCheck() {
        return isCheck;
    }

    public void setCheck(int check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
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


    //    "id" : 1,
//            "resourceId" : 1,
//            "showImage" : "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=958512776,2704315940&fm=58",
//            "desp" : "这是详情介绍",
//            "title" : "这是标题",
//            "contentUrl" : "static.test.mobioa.cn/star/files/20160111/6/6071969.mp4",
//            "playCount" : 123,
//            "chapter" : 1

}
