package com.wodm.android.bean;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/2
 */
public class DianZanBean {


    /**
     * code : 1000
     * message : 操作成功
     * data : [{"content":"小康测试回复你的","sendId":274,"sendNickName":"赵文友","times":"2016-12-05","sendPortrait":"http://59.108.94.55:8899/static//head/20161116/274/274280458.jpg","messageId":34},{"content":"小康测试回复你的","sendId":272,"sendNickName":"M514168","times":"2016-12-05","sendPortrait":"","messageId":33},{"content":"小康测试回复你的","sendId":271,"sendNickName":"一脸懵逼的猪","times":"2016-12-05","sendPortrait":"http://wx.qlogo.cn/mmopen/OibRNdtlJdkHm0OpxSxyK9APYFYJxAlAncm8XEOhmj1NFX2qu44uaOD3XiczB28Kjju4Qfzrcq88y49LMYRFgdB42uMI2vGNhV/0","messageId":32}]
     */

    private int code;
    private String message;
    /**
     * content : 小康测试回复你的
     * sendId : 274
     * sendNickName : 赵文友
     * times : 2016-12-05
     * sendPortrait : http://59.108.94.55:8899/static//head/20161116/274/274280458.jpg
     * messageId : 34
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
        private int messageId;//当前消息的ID

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

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }
    }
}
