package com.wodm.android.bean;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;

import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/6.
 * 获取所有的 有人@我的消息
 */

public class AtWoBean {

    /**
     * code : 1000
     * message : 操作成功
     * data : [{"content":"小康测试回复你的","sendId":198,"resourceId":554,"sendNickName":"小康康","times":"2016-12-12","sendPortrait":"","receiveNickName":"@VIP之小将之家乡，亲人","type":0,"messageId":107,"commentId":789}]
     */

    private int code;
    private String message;
    /**
     * content : 小康测试回复你的
     * sendId : 198
     * resourceId : 554
     * sendNickName : 小康康
     * times : 2016-12-12
     * sendPortrait :
     * receiveNickName : @VIP之小将之家乡，亲人
     * type : 0
     * messageId : 107
     * commentId : 789
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

    public static class DataBean implements Serializable{
        private String content;
        private int sendId;
        private int resourceId;
        private String sendNickName;
        private String times;
        private String sendPortrait;
        private String receiveNickName;
        private int type;
        private int messageId;
        private int commentId;

        public void intentToSendMsg(Context context){
            Intent intent = new Intent(context,SendMsgActivity.class);
            intent.putExtra(SendMsgActivity.ATWOBEAN, this);
            intent.putExtra("flag",2);
            intent.putExtra("content",getContent());
            intent.putExtra("name",getReceiveNickName());
            context.startActivity(intent);
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

        public int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }
    }
}
