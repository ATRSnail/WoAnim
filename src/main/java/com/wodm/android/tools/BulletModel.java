package com.wodm.android.tools;

import java.util.List;

/**
 * Created by KECB on 7/19/16.
 */

public class BulletModel {

    private List<BarrageListBean> barrageList;

    public List<BarrageListBean> getBarrageList() {
        return barrageList;
    }

    public void setBarrageList(List<BarrageListBean> barrageList) {
        this.barrageList = barrageList;
    }

    public static class BarrageListBean {
        private long id;
        private long utime;
        private String time;
        private String fontColor;
        private String vfPlayId;
        private String userId;
        private String state;
        private String context;
        private String fontSize;
        private long ctime;

        public int getSudu() {
            return sudu;
        }

        public void setSudu(int sudu) {
            this.sudu = sudu;
        }

        private int sudu;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUtime() {
            return utime;
        }

        public void setUtime(long utime) {
            this.utime = utime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }

        public String getVfPlayId() {
            return vfPlayId;
        }

        public void setVfPlayId(String vfPlayId) {
            this.vfPlayId = vfPlayId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }
    }

}
