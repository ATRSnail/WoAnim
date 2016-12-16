package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/11/30.
 */

public class FansBean {


    /**
     * count  =0 表示 已被关注 或者就是添加关注  =1 表示互粉
     * code : 1000
     * message : 操作成功
     * data : [{"gradeName":"萌新V","count":1,"nickName":"谁是我的大白菜，我要拱白菜。","fansUserId":229,"gradeValue":5,"portrait":"http://59.108.94.55:8899/static//head/20161215/229/229675152.jpg"},{"gradeName":"萌新II","count":1,"nickName":"M514168","fansUserId":272,"gradeValue":2,"portrait":""},{"gradeName":"萌新II","count":1,"nickName":"赵文友","fansUserId":274,"gradeValue":2,"portrait":"http://59.108.94.55:8899/static//head/20161214/274/274704063.jpg"},{"gradeName":"萌娃Ⅵ","count":1,"nickName":"VIP之小将之家乡，亲人","fansUserId":280,"gradeValue":6,"portrait":"http://59.108.94.55:8899/static//head/20161216/280/280510973.JPEG"},{"gradeName":"萌新I","count":1,"nickName":"随feng丶孤少","fansUserId":286,"gradeValue":1,"portrait":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLACGDuzhcibUWJV46C8wuVZEdicDQLicGK5bfLIh3eRYOhReYpTvTfpTuTtOXXr3W9PRbn9tJ11adclw/0"},{"gradeName":"萌新III","count":1,"nickName":"M006245","fansUserId":287,"gradeValue":3,"portrait":"http://59.108.94.55:8899/static//head/20161215/287/287279330.png"},{"gradeName":"萌新II","count":0,"nickName":"Simon_胡","fansUserId":291,"gradeValue":2,"portrait":"http://wx.qlogo.cn/mmopen/Mx6ia3yAoJd8EQLIyTVLpDBdq36P1RM9DsGYjbic4UpCibpDE2nrOwByicyzrahtKrn6Q8eegCH4JZZGpOXBD6kn9IfZicThnFoor/0"}]
     */

    private int code;
    private String message;
    /**
     * gradeName : 萌新V
     * count : 1
     * nickName : 谁是我的大白菜，我要拱白菜。
     * fansUserId : 229
     * gradeValue : 5
     * portrait : http://59.108.94.55:8899/static//head/20161215/229/229675152.jpg
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String gradeName;
        private int count;
        private String nickName;
        private int fansUserId;
        private int gradeValue;
        private String portrait;

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getFansUserId() {
            return fansUserId;
        }

        public void setFansUserId(int fansUserId) {
            this.fansUserId = fansUserId;
        }

        public int getGradeValue() {
            return gradeValue;
        }

        public void setGradeValue(int gradeValue) {
            this.gradeValue = gradeValue;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }
    }
}
