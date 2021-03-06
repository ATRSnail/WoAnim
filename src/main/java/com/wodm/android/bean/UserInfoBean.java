package com.wodm.android.bean;

/**
 * Created by songchenyu on 16/10/9.
 */

public class UserInfoBean {


    /**
     * code : 1000
     * message : 数据获取成功
     * data : {"currentEmpirical":0,"token":null,"account":{"id":143,"appkey":"","uuid":"03c661af0aa680f3fa7705259bc19399","udid":"f35fe3d2366cf53c94a7bf87e849aa7f","nickName":"Chhhhgg","registTime":1474978930000,"sex":0,"birthday":"","mobile":"18519206703","email":"","portrait":"http://172.16.2.125:8899/static//head/20160927/143/143690449.JPEG","type":0,"autograph":"wwwfttgf000tttttttthhh","osName":"Android","openId":null,"unionId":null,"plainPassword":"","vip_mine":1,"vipFlag":1,"gradeValue":1,"empiricalValue":0,"gradeName":"萌新I","gradeImageId":1},"needEmpirical":12}
     */

    private int code;
    private String message;
    /**
     * nextGradeEmpirical : 12
     * currentEmpirical : 0
     * needScore : 4
     * checkinCount : 0
     * pandentDetail : {"imgUrlGJ":"http://mk.wo.com.cn/static/goods/6.png","codeTXK":"008","nameTXK":"潜水员","codeGJ":"006","imgUrlTXK":"http://mk.wo.com.cn/static/goods/8.png","nameGJ":"心型眼镜"}
     * cartoonCount : 0
     * countScore : 4
     * account : {"id":198,"appkey":"","uuid":"","udid":"","nickName":"M197406","registTime":1477383788000,"sex":0,"birthday":"","mobile":"17777813895","email":"","score":2700,"portrait":"","type":0,"autograph":"","osName":"","openId":"","unionId":"哈哈","plainPassword":"","vip":2,"vipFlag":14,"gradeValue":1,"empiricalValue":10,"gradeName":"萌新I","gradeImageId":null}
     * needEmpirical : 2
     * animationCount : 0
     * currentScore : 0
     * maxCheckinCount : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int nextGradeEmpirical;
        private int currentEmpirical;
        private int needScore;
        private int checkinCount;
        /**
         * imgUrlGJ : http://mk.wo.com.cn/static/goods/6.png
         * codeTXK : 008
         * nameTXK : 潜水员
         * codeGJ : 006
         * imgUrlTXK : http://mk.wo.com.cn/static/goods/8.png
         * nameGJ : 心型眼镜
         */

        private PandentDetailBean pandentDetail;
        private int cartoonCount;
        private int countScore;
        /**
         * id : 198
         * appkey :
         * uuid :
         * udid :
         * nickName : M197406
         * registTime : 1477383788000
         * sex : 0
         * birthday :
         * mobile : 17777813895
         * email :
         * score : 2700
         * portrait :
         * type : 0
         * autograph :
         * osName :
         * openId :
         * unionId : 哈哈
         * plainPassword :
         * vip : 2
         * vipFlag : 14
         * gradeValue : 1
         * empiricalValue : 10
         * gradeName : 萌新I
         * gradeImageId : null
         *  followNum" : 4, 关注数
         *  "fansNum" : 4,粉丝数
         *  isFollow 1 关注 0 未关注 用户是否已被关注
         */

        private long followNum;
        private long fansNum;
        private int isFollow;

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        private AccountBean account;
        private int needEmpirical;
        private int animationCount;
        private int currentScore;

        public long getFollowNum() {
            return followNum;
        }

        public void setFollowNum(long followNum) {
            this.followNum = followNum;
        }

        public long getFansNum() {
            return fansNum;
        }

        public void setFansNum(long fansNum) {
            this.fansNum = fansNum;
        }

        private int maxCheckinCount;
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private String token;

        public int getNextGradeEmpirical() {
            return nextGradeEmpirical;
        }

        public void setNextGradeEmpirical(int nextGradeEmpirical) {
            this.nextGradeEmpirical = nextGradeEmpirical;
        }

        public int getCurrentEmpirical() {
            return currentEmpirical;
        }

        public void setCurrentEmpirical(int currentEmpirical) {
            this.currentEmpirical = currentEmpirical;
        }

        public int getNeedScore() {
            return needScore;
        }

        public void setNeedScore(int needScore) {
            this.needScore = needScore;
        }

        public int getCheckinCount() {
            return checkinCount;
        }

        public void setCheckinCount(int checkinCount) {
            this.checkinCount = checkinCount;
        }

        public PandentDetailBean getPandentDetail() {
            return pandentDetail;
        }

        public void setPandentDetail(PandentDetailBean pandentDetail) {
            this.pandentDetail = pandentDetail;
        }

        public int getCartoonCount() {
            return cartoonCount;
        }

        public void setCartoonCount(int cartoonCount) {
            this.cartoonCount = cartoonCount;
        }

        public int getCountScore() {
            return countScore;
        }

        public void setCountScore(int countScore) {
            this.countScore = countScore;
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

        public int getAnimationCount() {
            return animationCount;
        }

        public void setAnimationCount(int animationCount) {
            this.animationCount = animationCount;
        }

        public int getCurrentScore() {
            return currentScore;
        }

        public void setCurrentScore(int currentScore) {
            this.currentScore = currentScore;
        }

        public int getMaxCheckinCount() {
            return maxCheckinCount;
        }

        public void setMaxCheckinCount(int maxCheckinCount) {
            this.maxCheckinCount = maxCheckinCount;
        }

        public static class PandentDetailBean {
            private String imgUrlGJ;
            private String codeTXK;
            private String nameTXK;
            private String codeGJ;
            private String imgUrlTXK;
            private String nameGJ;

            public String getImgUrlGJ() {
                return imgUrlGJ;
            }

            public void setImgUrlGJ(String imgUrlGJ) {
                this.imgUrlGJ = imgUrlGJ;
            }

            public String getCodeTXK() {
                return codeTXK;
            }

            public void setCodeTXK(String codeTXK) {
                this.codeTXK = codeTXK;
            }

            public String getNameTXK() {
                return nameTXK;
            }

            public void setNameTXK(String nameTXK) {
                this.nameTXK = nameTXK;
            }

            public String getCodeGJ() {
                return codeGJ;
            }

            public void setCodeGJ(String codeGJ) {
                this.codeGJ = codeGJ;
            }

            public String getImgUrlTXK() {
                return imgUrlTXK;
            }

            public void setImgUrlTXK(String imgUrlTXK) {
                this.imgUrlTXK = imgUrlTXK;
            }

            public String getNameGJ() {
                return nameGJ;
            }

            public void setNameGJ(String nameGJ) {
                this.nameGJ = nameGJ;
            }
        }

        public static class AccountBean {
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
            private int score;
            private String portrait;
            private int type;
            private String autograph;
            private String osName;
            private String openId;
            private String unionId;
            private String plainPassword;
            private int vip;
            private int vipFlag;
            private int gradeValue;
            private int empiricalValue;
            private String gradeName;
            private Object gradeImageId;

            @Override
            public String toString() {
                return "AccountBean{" +
                        "id=" + id +
                        ", appkey='" + appkey + '\'' +
                        ", uuid='" + uuid + '\'' +
                        ", udid='" + udid + '\'' +
                        ", nickName='" + nickName + '\'' +
                        ", registTime=" + registTime +
                        ", sex=" + sex +
                        ", birthday='" + birthday + '\'' +
                        ", mobile='" + mobile + '\'' +
                        ", email='" + email + '\'' +
                        ", score=" + score +
                        ", portrait='" + portrait + '\'' +
                        ", type=" + type +
                        ", autograph='" + autograph + '\'' +
                        ", osName='" + osName + '\'' +
                        ", openId='" + openId + '\'' +
                        ", unionId='" + unionId + '\'' +
                        ", plainPassword='" + plainPassword + '\'' +
                        ", vip=" + vip +
                        ", vipFlag=" + vipFlag +
                        ", gradeValue=" + gradeValue +
                        ", empiricalValue=" + empiricalValue +
                        ", gradeName='" + gradeName + '\'' +
                        ", gradeImageId=" + gradeImageId +
                        '}';
            }

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

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
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

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public String getUnionId() {
                return unionId;
            }

            public void setUnionId(String unionId) {
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

            public Object getGradeImageId() {
                return gradeImageId;
            }

            public void setGradeImageId(Object gradeImageId) {
                this.gradeImageId = gradeImageId;
            }
        }
    }


  
}
