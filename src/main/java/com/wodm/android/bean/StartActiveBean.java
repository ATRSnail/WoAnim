package com.wodm.android.bean;

/**
 * Created by songchenyu on 17/1/5.
 */

public class StartActiveBean {
    /**活动名称*/
    private String hdname;
    /**客户端弹出商品图片*/
    private String goodimage;

    /**红包小图标*/
    private String hbimage;

    /**活动的表示 0:表示不开放 1:表示开放*/
    private int flag=0;

    /**赠送的活动商品的编号*/
    private String productCode="";

    public String getHdname() {
        return hdname;
    }

    public void setHdname(String hdname) {
        this.hdname = hdname;
    }

    public String getGoodimage() {
        return goodimage;
    }

    public void setGoodimage(String goodimage) {
        this.goodimage = goodimage;
    }

    public String getHbimage() {
        return hbimage;
    }

    public void setHbimage(String hbimage) {
        this.hbimage = hbimage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
