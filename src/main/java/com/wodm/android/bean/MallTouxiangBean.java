package com.wodm.android.bean;

import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class MallTouxiangBean {


    /**
     * code : 1000
     * message : 获取信息成功
     * data : [{"id":67,"productName":"白兔子","productType":5,"productImageUrl":"http://172.16.2.125/static/1479261969143.png","productCode":"1479261970387","productNumber":0,"price":0,"needScore":0,"flag":0},{"id":68,"productName":"宝石绿帽子","productType":5,"productImageUrl":"http://172.16.2.125/static/1479261995515.png","productCode":"1479261998123","productNumber":0,"price":0,"needScore":0,"flag":0},{"id":69,"productName":"粉兔子","productType":5,"productImageUrl":"http://172.16.2.125/static/1479262016755.png","productCode":"1479262017786","productNumber":0,"price":0,"needScore":0,"flag":0},{"id":70,"productName":"风镜帽","productType":5,"productImageUrl":"http://172.16.2.125/static/1479262065121.png","productCode":"1479262065369","productNumber":500,"price":0,"needScore":0,"flag":0},{"id":71,"productName":"海盗帽子","productType":5,"productImageUrl":"http://172.16.2.125/static/1479262078844.png","productCode":"1479262079098","productNumber":0,"price":0,"needScore":0,"flag":0},{"id":72,"productName":"麋鹿","productType":5,"productImageUrl":"http://172.16.2.125/static/1479262090271.png","productCode":"1479262090862","productNumber":0,"price":0,"needScore":0,"flag":0}]
     */

    private int code;
    private String message;
    /**
     * id : 67
     * productName : 白兔子
     * productType : 5
     * productImageUrl : http://172.16.2.125/static/1479261969143.png
     * productCode : 1479261970387
     * productNumber : 0
     * price : 0.0
     * needScore : 0
     * flag : 0
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
        private int productNumber;
        private double price;
        private int needScore;
        private int flag;

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

        public int getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(int productNumber) {
            this.productNumber = productNumber;
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

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}

