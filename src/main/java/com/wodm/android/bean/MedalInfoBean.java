package com.wodm.android.bean;


import java.util.List;

/**
 * Created by ATRSnail on 2016/10/17.
 */

public class MedalInfoBean {

    /** medalSource 勋章来源(1：签到 2：节日活动 3：购买vip 4：新手任务 5：线下活动)
     * @param medalType 1：新手勋章 2：vip勋章 3：vvip勋章
     * 4：签到1天  5：签到3天 6：签到7天 7：签到30天 8：签到3个月 9：签到6个月 10：签到1年
     * @return
     */
    /**
     * code : 1000
     * message : 数据获取成功
     * data : [{"id":8,"medal":{"id":6,"medalSource":2,"medalImage":"2222","medalType":1,"needCharges":0,"status":1,"createTime":1477391365000},"operationTime":1476613730000,"status":1},{"id":2,"medal":{"id":2,"medalSource":1,"medalImage":"http://202.106.63.82/static/1470033529421.jpg","medalType":4,"needCharges":0,"status":1,"createTime":1475980793000},"operationTime":null,"status":1},{"id":6,"medal":{"id":5,"medalSource":3,"medalImage":"http://202.106.63.82/static/1470033529421.jpg","medalType":2,"needCharges":0,"status":1,"createTime":1476597925000},"operationTime":null,"status":1}]
     */

    private int code;
    private String message;
    /**
     * id : 8
     * medal : {"id":6,"medalSource":2,"medalImage":"2222","medalType":1,"needCharges":0,"status":1,"createTime":1477391365000}
     * operationTime : 1476613730000
     * status : 1
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
        /**
         * id : 6
         * medalSource : 2
         * medalImage : 2222
         * medalType : 1
         * needCharges : 0
         * status : 1
         * createTime : 1477391365000
         */

        private MedalBean medal;
        private long operationTime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MedalBean getMedal() {
            return medal;
        }

        public void setMedal(MedalBean medal) {
            this.medal = medal;
        }

        public long getOperationTime() {
            return operationTime;
        }

        public void setOperationTime(long operationTime) {
            this.operationTime = operationTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class MedalBean {
            private int id;
            private int medalSource;
            private String medalImage;
            private int medalType;
            private int needCharges;
            private int status;
            private long createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMedalSource() {
                return medalSource;
            }

            public void setMedalSource(int medalSource) {
                this.medalSource = medalSource;
            }

            public String getMedalImage() {
                return medalImage;
            }

            public void setMedalImage(String medalImage) {
                this.medalImage = medalImage;
            }

            public int getMedalType() {
                return medalType;
            }

            public void setMedalType(int medalType) {
                this.medalType = medalType;
            }

            public int getNeedCharges() {
                return needCharges;
            }

            public void setNeedCharges(int needCharges) {
                this.needCharges = needCharges;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }


}
