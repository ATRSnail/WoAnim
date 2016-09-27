package com.wodm.android.bean;

public enum DowmStatus {
        WAIT(0), //等待下载
        PAUSE(1), //暂停下载
        LOGDING(2), //正在下载
        ERROR(3), //下载错误、失败
        FINISH(4); //下载完成
        private int value = 0;

        DowmStatus(int value) {
            this.value = value;
        }

        public static DowmStatus valueOf(int value) {
            switch (value) {
                case 0:
                    return WAIT;
                case 1:
                    return PAUSE;
                case 2:
                    return LOGDING;
                case 3:
                    return ERROR;
                case 4:
                    return FINISH;
            }
            return ERROR;
        }
    }