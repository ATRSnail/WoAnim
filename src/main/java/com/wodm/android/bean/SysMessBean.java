package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/6.
 */

public class SysMessBean {

    /**
     * 系统消息的数据
     * code : 1000
     * message : 操作成功
     * data : [{"id":40,"type":1,"createTime":1480994389000,"content":"您花费了0个积分,成功兑换了个头像框:企鹅仔","userId":280,"status":1,"contentId":2253,"contentType":5,"times":"2016-12-06"},{"id":39,"type":1,"createTime":1480994370000,"content":"您花费了0个积分,成功兑换了个头像框:青蛙","userId":280,"status":1,"contentId":2252,"contentType":5,"times":"2016-12-06"},{"id":38,"type":1,"createTime":1480994295000,"content":"您花费了0个积分,成功兑换了个头像框:海盗帽子","userId":280,"status":1,"contentId":2251,"contentType":5,"times":"2016-12-06"}]
     */

    private int code;
    private String message;
    /**
     * id : 40
     * type : 1
     * createTime : 1480994389000
     * content : 您花费了0个积分,成功兑换了个头像框:企鹅仔
     * userId : 280
     * status : 1
     * contentId : 2253
     * contentType : 5
     * times : 2016-12-06
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
        private int id;
        private int type;
        private long createTime;
        private String content;
        private int userId;
        private int status;
        private int contentId;
        private int contentType;
        private String times;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getContentId() {
            return contentId;
        }

        public void setContentId(int contentId) {
            this.contentId = contentId;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
