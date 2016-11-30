package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/11/30.
 */

public class FollowBean {

    /**
     * code : 1000
     * message : 操作成功
     * data : [{"nickName":"风","followUserId":228,"gradeValue":1,"portrait":"http://wx.qlogo.cn/mmopen/DrNKBR4QfgRkfOl8pWSR0icLY1uicMCga1BwYhO5of6J7te6pzthk3gmoquG6CqPMKFwM87ic5P0RZg0XXfkiaF0YQ/0"},{"nickName":"M520","followUserId":270,"gradeValue":1,"portrait":"http://59.108.94.55:8899/static//head/20161110/270/270436991.gif"},{"nickName":"一脸懵逼的猪","followUserId":271,"gradeValue":1,"portrait":"http://wx.qlogo.cn/mmopen/OibRNdtlJdkHm0OpxSxyK9APYFYJxAlAncm8XEOhmj1NFX2qu44uaOD3XiczB28Kjju4Qfzrcq88y49LMYRFgdB42uMI2vGNhV/0"},{"nickName":"赵文友","followUserId":274,"gradeValue":1,"portrait":"http://59.108.94.55:8899/static//head/20161116/274/274280458.jpg"}]
     */

    private int code;
    private String message;
    /**
     * nickName : 风
     * followUserId : 228
     * gradeValue : 1
     * portrait : http://wx.qlogo.cn/mmopen/DrNKBR4QfgRkfOl8pWSR0icLY1uicMCga1BwYhO5of6J7te6pzthk3gmoquG6CqPMKFwM87ic5P0RZg0XXfkiaF0YQ/0
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
        private String nickName;
        private int followUserId;
        private int gradeValue;
        private String portrait;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getFollowUserId() {
            return followUserId;
        }

        public void setFollowUserId(int followUserId) {
            this.followUserId = followUserId;
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
