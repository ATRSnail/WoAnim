package com.wodm.android.bean;

/**
 * Created by songchenyu on 16/10/9.
 */

public class UserInfoBean {


    /**
     * code : 1000
     * message : 数据获取成功
     * data : {"currentEmpirical":0,"token":null,"account":{"id":143,"appkey":"","uuid":"03c661af0aa680f3fa7705259bc19399","udid":"f35fe3d2366cf53c94a7bf87e849aa7f","nickName":"Chhhhgg","registTime":1474978930000,"sex":0,"birthday":"","mobile":"18519206703","email":"","portrait":"http://172.16.2.125:8899/static//head/20160927/143/143690449.JPEG","type":0,"autograph":"wwwfttgf000tttttttthhh","osName":"Android","openId":null,"unionId":null,"plainPassword":"","vip":1,"vipFlag":1,"gradeValue":1,"empiricalValue":0,"gradeName":"萌新I","gradeImageId":1},"needEmpirical":12}
     */

    private int code;
    private String message;
    /**
     * currentEmpirical : 0
     * token : null
     * account : {"id":143,"appkey":"","uuid":"03c661af0aa680f3fa7705259bc19399","udid":"f35fe3d2366cf53c94a7bf87e849aa7f","nickName":"Chhhhgg","registTime":1474978930000,"sex":0,"birthday":"","mobile":"18519206703","email":"","portrait":"http://172.16.2.125:8899/static//head/20160927/143/143690449.JPEG","type":0,"autograph":"wwwfttgf000tttttttthhh","osName":"Android","openId":null,"unionId":null,"plainPassword":"","vip":1,"vipFlag":1,"gradeValue":1,"empiricalValue":0,"gradeName":"萌新I","gradeImageId":1}
     * needEmpirical : 12
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int currentEmpirical;//今日经验
        private Object token;


        /**
         * id : 143
         * appkey :
         * uuid : 03c661af0aa680f3fa7705259bc19399
         * udid : f35fe3d2366cf53c94a7bf87e849aa7f
         * nickName : Chhhhgg
         * registTime : 1474978930000
         * sex : 0
         * birthday :
         * mobile : 18519206703
         * email :
         * portrait : http://172.16.2.125:8899/static//head/20160927/143/143690449.JPEG
         * type : 0
         * autograph : wwwfttgf000tttttttthhh
         * osName : Android
         * openId : null
         * unionId : null
         * plainPassword :
         * 用户的会员类型0:普通 1:vip 2:vvip
         * vip : 1
         * vipFlag : 1
         * gradeValue : 1
         * empiricalValue : 0
         * gradeName : 萌新I
         * gradeImageId : 1
         * maxCheckinCount //累计签到天数
         * checkinCount //连续签到天数
         * map.put("account", account);
         * map.put("currentEmpirical", currentEmpirical);//今日所获得的经验
         * map.put("needEmpirical", needEmpirical);//升到下一级还差多少的经验
         * map.put("nextGradeEmpirical", nextGradeEmpirical);//下一级所需要的经验值
         * map.put("maxCheckinCount", maxCheckinCount);//累计签到天数
         * map.put("checkinCount", checkinCount);//连续签到天数
         * map.put("countScore", countScore);//今日总共能获得的积分
         * map.put("currentScore", currentScore);//今日获得的积分
         * map.put("needScore", needScore);//今日未获得的积分
         * /**任务类型(1：日常 2：新手 3：临时任务 4：临时活动)
         * private int taskType;
         * 0：非 1：签到经验 2：观看动漫 3：发评论 4：完善个人资料
         * 5：使用搜索功能 6：点击一次广告 7：查看一次新闻
         * 8：添加一个好友 9：点一次赞 10：打赏一次 11：敬请期待
         * private int taskValue;
         * :newuser/perfectUserInfo
         * Long userId   用户的id
         * int taskType, 任务的大类 2
         * int taskValue 任务的小类  4
         */

        private AccountBean account;
        private int needEmpirical;

        public int getNextGradeEmpirical() {
            return nextGradeEmpirical;
        }

        public void setNextGradeEmpirical(int nextGradeEmpirical) {
            this.nextGradeEmpirical = nextGradeEmpirical;
        }

        private float wallet;

        public float getWallet() {
            return wallet;
        }

        public void setWallet(float wallet) {
            this.wallet = wallet;
        }


        private int nextGradeEmpirical;

        public int getCurrentEmpirical() {
            return currentEmpirical;
        }

        public void setCurrentEmpirical(int currentEmpirical) {
            this.currentEmpirical = currentEmpirical;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public int getNeedEmpirical() {
            return needEmpirical;
        }

        public void setNeedEmpirical(int needEmpirical) {
            this.needEmpirical = needEmpirical;
        }

        public static class AccountBean {
            private int score;//积分
            private int id;
            private String appkey;
            private String uuid;
            private String udid;
            private String nickName;
            private long registTime;
            private int sex;
            private String birthday;
            private String mobile;
            private String email;
            private String portrait;
            private int type;
            private String autograph;
            private String osName;
            private Object openId;
            private Object unionId;
            private String plainPassword;
            private int vip;
            private int vipFlag;
            private int gradeValue;
            private int empiricalValue;//累计经验
            private String gradeName;
            private int gradeImageId;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getCountScore() {
                return countScore;
            }

            public void setCountScore(int countScore) {
                this.countScore = countScore;
            }

            public int getCurrentScore() {
                return currentScore;
            }

            public void setCurrentScore(int currentScore) {
                this.currentScore = currentScore;
            }

            public int getNeedScore() {
                return needScore;
            }

            public void setNeedScore(int needScore) {
                this.needScore = needScore;
            }

            private int countScore;
            private int currentScore;
            private int needScore;

            public int getMaxCheckinCount() {
                return maxCheckinCount;
            }

            public void setMaxCheckinCount(int maxCheckinCount) {
                this.maxCheckinCount = maxCheckinCount;
            }

            public int getCheckinCount() {
                return checkinCount;
            }

            public void setCheckinCount(int checkinCount) {
                this.checkinCount = checkinCount;
            }

            private int maxCheckinCount;
            private int checkinCount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAppkey() {
                return appkey;
            }

            public void setAppkey(String appkey) {
                this.appkey = appkey;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getUdid() {
                return udid;
            }

            public void setUdid(String udid) {
                this.udid = udid;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public long getRegistTime() {
                return registTime;
            }

            public void setRegistTime(long registTime) {
                this.registTime = registTime;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getOsName() {
                return osName;
            }

            public void setOsName(String osName) {
                this.osName = osName;
            }

            public Object getOpenId() {
                return openId;
            }

            public void setOpenId(Object openId) {
                this.openId = openId;
            }

            public Object getUnionId() {
                return unionId;
            }

            public void setUnionId(Object unionId) {
                this.unionId = unionId;
            }

            public String getPlainPassword() {
                return plainPassword;
            }

            public void setPlainPassword(String plainPassword) {
                this.plainPassword = plainPassword;
            }

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public int getVipFlag() {
                return vipFlag;
            }

            public void setVipFlag(int vipFlag) {
                this.vipFlag = vipFlag;
            }

            public int getGradeValue() {
                return gradeValue;
            }

            public void setGradeValue(int gradeValue) {
                this.gradeValue = gradeValue;
            }

            public int getEmpiricalValue() {
                return empiricalValue;
            }

            public void setEmpiricalValue(int empiricalValue) {
                this.empiricalValue = empiricalValue;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }

            public int getGradeImageId() {
                return gradeImageId;
            }

            public void setGradeImageId(int gradeImageId) {
                this.gradeImageId = gradeImageId;
            }
        }
    }
}
