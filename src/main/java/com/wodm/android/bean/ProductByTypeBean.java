package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2016/11/17.
 */

public class ProductByTypeBean {


    /**
     * code : 1000
     * message : 获取信息成功
     * data : [{"id":4,"productName":"vip一个月","productType":2,"productImageUrl":"http://mk.wo.com.cn/static/goods/4.png","productCode":"004","price":20,"needScore":222,"discountPrice":35}]
     */

    private int code;
    private String message;
    /**
     * id : 4
     * productName : vip一个月
     * productType : 2
     * productImageUrl : http://mk.wo.com.cn/static/goods/4.png
     * productCode : 004
     * price : 20.0
     * needScore : 222
     * discountPrice : 35.0
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
        private String productName;
        private int productType;
        private String productImageUrl;
        private String productCode;
        private double price;
        private int needScore;
        private double discountPrice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getNeedScore() {
            return needScore;
        }

        public void setNeedScore(int needScore) {
            this.needScore = needScore;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }
    }
}
