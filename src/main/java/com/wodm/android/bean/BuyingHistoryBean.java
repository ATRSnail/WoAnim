package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/11/17.
 */

public class BuyingHistoryBean {

    /**
     * code : 1000
     * message : 获取信息成功
     * data : [{"productName":"vip一个月","productImageUrl":"http://mk.wo.com.cn/static/goods/4.png","productCode":"004","numberflag":"222积分","productTypeFlag":"VIP会员服务","description":"交易成功","flag":1},{"productName":"vip一个月","productImageUrl":"http://mk.wo.com.cn/static/goods/4.png","productCode":"004","numberflag":"¥20.0","productTypeFlag":"VIP会员服务","description":"交易成功","flag":1},{"productName":"积分20","productImageUrl":"http://mk.wo.com.cn/static/goods/13.png","productCode":"013","numberflag":"222积分","productTypeFlag":"积分赠送","description":"交易成功","flag":1}]
     */

    private int code;
    private String message;
    /**
     * productName : vip一个月
     * productImageUrl : http://mk.wo.com.cn/static/goods/4.png
     * productCode : 004
     * numberflag : 222积分
     * productTypeFlag : VIP会员服务
     * description : 交易成功
     * flag : 1
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
        private String productName;
        private String productImageUrl;
        private String productCode;
        private String numberflag;
        private String productTypeFlag;
        private String description;
        private int flag;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImageUrl() {
            return productImageUrl;
        }

        public void setProductImageUrl(String productImageUrl) {
            this.productImageUrl = productImageUrl;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getNumberflag() {
            return numberflag;
        }

        public void setNumberflag(String numberflag) {
            this.numberflag = numberflag;
        }

        public String getProductTypeFlag() {
            return productTypeFlag;
        }

        public void setProductTypeFlag(String productTypeFlag) {
            this.productTypeFlag = productTypeFlag;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}
