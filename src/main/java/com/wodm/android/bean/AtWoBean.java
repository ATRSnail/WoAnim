package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/6.
 */

public class AtWoBean {

    /**
     * 获取所有的 有人@我的消息
     * code : 1000
     * message : 操作成功
     * data : [{"content":"在一起你妹的","sendId":198,"sendNickName":"小康康","times":"2016-12-06","sendPortrait":"","receiveNickName":"@VIP之小将之家乡，亲人"},{"content":"小康测试回复你的","sendId":198,"sendNickName":"小康康","times":"2016-12-06","sendPortrait":"","receiveNickName":"@VIP之小将之家乡，亲人"},{"content":"🐷[em16][em9][em9][em9][em9][em19][em18][em10][em12][em5][em13][em9][em10][em3][em9][em2][em1][em9][em10][em2][em2][em10][em3][em2][em13][em12][em19][em3][em2][em10][em10][em10]","sendId":271,"sendNickName":"一脸懵逼的猪","times":"2016-12-06","sendPortrait":"http://wx.qlogo.cn/mmopen/OibRNdtlJdkHm0OpxSxyK9APYFYJxAlAncm8XEOhmj1NFX2qu44uaOD3XiczB28Kjju4Qfzrcq88y49LMYRFgdB42uMI2vGNhV/0","receiveNickName":"@VIP之小将之家乡，亲人"}]
     */

    private int code;
    private String message;
    /**
     * content : 在一起你妹的
     * sendId : 198
     * sendNickName : 小康康
     * times : 2016-12-06
     * sendPortrait :
     * receiveNickName : @VIP之小将之家乡，亲人
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
        private String content;
        private int sendId;
        private String sendNickName;
        private String times;
        private String sendPortrait;
        private String receiveNickName;
        private int messageId;//当前消息的ID

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSendId() {
            return sendId;
        }

        public void setSendId(int sendId) {
            this.sendId = sendId;
        }

        public String getSendNickName() {
            return sendNickName;
        }

        public void setSendNickName(String sendNickName) {
            this.sendNickName = sendNickName;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getSendPortrait() {
            return sendPortrait;
        }

        public void setSendPortrait(String sendPortrait) {
            this.sendPortrait = sendPortrait;
        }

        public String getReceiveNickName() {
            return receiveNickName;
        }

        public void setReceiveNickName(String receiveNickName) {
            this.receiveNickName = receiveNickName;
        }
    }
}
