package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/2.
 */

public class XiaoXiBean {

    /**
     * code : 1000
     * message : 获取成功
     * data : [{"id":21,"type":1,"createTime":1480494056000,"content":"最新的系统消息","userId":280,"status":0,"times":"2016-11-30"},{"id":6,"type":2,"createTime":1480490110000,"content":"新的评3","userId":280,"status":0,"times":"2016-11-30"},{"id":9,"type":3,"createTime":1480490117000,"content":"有人@你了3","userId":280,"status":0,"times":"2016-11-30"},{"id":12,"type":4,"createTime":1480490169000,"content":"官方推送3","userId":280,"status":0,"times":"2016-11-30"},{"id":19,"type":5,"createTime":1480490222000,"content":"有人赞你3","userId":280,"status":0,"times":"2016-11-30"}]
     */

    private int code;
    private String message;
    /**
     * id : 21
     * type : 1
     * createTime : 1480494056000
     * content : 最新的系统消息
     * userId : 280
     * status : 0
     * times : 2016-11-30
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

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
