package com.wodm.android.bean;

import java.io.Serializable;

/**
 * Created by json on 2016/4/27.
 */
public class ObjectBean implements Serializable {
    /**
     * id : 555
     * name : 猫的诱惑
     * categoryId : 1
     * categoryName : 校园
     * copyrightId : 68
     * type : 1
     * areaId : 4
     * author : 橘文泠
     * yearId : 4
     * showImage : http://202.106.63.82/static/1477638833938.jpg
     * desp : 独自生活在白石的男孩赵尔儒在某个雨天捡回了四只流浪猫，没想到其中最调皮的黑猫希希竟在夜里变成了美丽的女孩。赵尔儒与希希度过了很多美好的时光，成为了彼此不可或缺的存在。希希的姐姐千湖月带来一个叫晓伊的女孩，拜托尔儒和希希帮忙照顾一段时间，没想到晓伊的出现竟然给尔儒带来无数的谜团。东郊神秘的古宅、会浮现出暗语的古书、“镜华之间”的钥匙……
     * score : 1
     * chapter : 6
     * playCount : 36199
     * commentCount : 17
     * goodCount : 0
     * collectionCount : 1
     * nowChapter : 1
     * nowChapterId : 0
     * title : 猫的诱惑
     * resourceType : 2
     * briefDesp : 独自生活在白石的男孩赵尔儒在某个雨天捡回了四只流浪猫，没想到其中最调皮的黑猫希希竟在夜里变成了美丽的女孩。赵尔儒与希希度过了很多美好的时光，成为了彼此不可或缺的存在。希希的姐姐千湖月带来一个叫晓伊的女孩，拜托尔儒和希希帮忙照顾一段时间，没想到晓伊的出现竟然给尔儒带来无数的谜团。东郊神秘的古宅、会浮现出暗语的古书、“镜华之间”的钥匙……
     * popularity : 3.6万
     * isCollect : 1
     * image_480_126 : http://202.106.63.82/static/1477638833957.jpg
     * image_480_160 : http://202.106.63.82/static/1477638833907.jpg
     * image_296_296 : http://202.106.63.82/static/1477638833938.jpg
     * image_300_400 : http://202.106.63.82/static/1477561384954.jpg
     * image_800_450 : http://202.106.63.82/static/1477561384879.jpg
     * image_900_300 : http://202.106.63.82/static/1477561384705.jpg
     * orijinalImage : http://202.106.63.82/static/1477561385029.jpg
     * scoreDesp : 糟糕番
     * lastTime : 2016-10-27 17:47:42
     */

    private int id;
    private String name;
    private int categoryId;
    private String categoryName;
    private int copyrightId;
    private int type;
    private int areaId;
    private String author;
    private int yearId;
    private String showImage;
    private String desp;
    private int score;
    private int chapter;
    private int playCount;
    private int commentCount;
    private int goodCount;
    private int collectionCount;
    private int nowChapter;
    private int nowChapterId;
    private String title;
    private int resourceType;
    private String briefDesp;
    private String popularity;
    private int isCollect;
    private String image_480_126;
    private String image_480_160;
    private String image_296_296;
    private String image_300_400;
    private String image_800_450;
    private String image_900_300;
    private String orijinalImage;
    private String scoreDesp;
    private String lastTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(int copyrightId) {
        this.copyrightId = copyrightId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getNowChapter() {
        return nowChapter;
    }

    public void setNowChapter(int nowChapter) {
        this.nowChapter = nowChapter;
    }

    public int getNowChapterId() {
        return nowChapterId;
    }

    public void setNowChapterId(int nowChapterId) {
        this.nowChapterId = nowChapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getBriefDesp() {
        return briefDesp;
    }

    public void setBriefDesp(String briefDesp) {
        this.briefDesp = briefDesp;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getImage_480_126() {
        return image_480_126;
    }

    public void setImage_480_126(String image_480_126) {
        this.image_480_126 = image_480_126;
    }

    public String getImage_480_160() {
        return image_480_160;
    }

    public void setImage_480_160(String image_480_160) {
        this.image_480_160 = image_480_160;
    }

    public String getImage_296_296() {
        return image_296_296;
    }

    public void setImage_296_296(String image_296_296) {
        this.image_296_296 = image_296_296;
    }

    public String getImage_300_400() {
        return image_300_400;
    }

    public void setImage_300_400(String image_300_400) {
        this.image_300_400 = image_300_400;
    }

    public String getImage_800_450() {
        return image_800_450;
    }

    public void setImage_800_450(String image_800_450) {
        this.image_800_450 = image_800_450;
    }

    public String getImage_900_300() {
        return image_900_300;
    }

    public void setImage_900_300(String image_900_300) {
        this.image_900_300 = image_900_300;
    }

    public String getOrijinalImage() {
        return orijinalImage;
    }

    public void setOrijinalImage(String orijinalImage) {
        this.orijinalImage = orijinalImage;
    }

    public String getScoreDesp() {
        return scoreDesp;
    }

    public void setScoreDesp(String scoreDesp) {
        this.scoreDesp = scoreDesp;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
//    private String name;
//    /** 作品分类ID */
//    private Long categoryId = 0l;
//    /** 作品版权ID */
//    private Long copyrightId = 0l;
//    /** 1 连载   2 全本*/
//    private int type = 1;
//    /** CP ID*/
//    @JsonIgnore
//    private int cpId = 0;
//    /** 区域 ID*/
//    private int areaId=0;
//    /** 作者*/
//    private String author="";
//
//    /** 1 有效  0 无效*/
//    @JsonIgnore
//    private int status=1;
//
//
//    /** 修改人*/
//    @JsonIgnore
//    private String modifyBy="";
//
//
//    /** 修改时间*/
//    @JsonIgnore
//    private Date modifyTime = new Date();
//
//
//    /** 创建人*/
//    @JsonIgnore
//    private String createBy="";
//
//
//    /** 创建时间*/
//    @JsonIgnore
//    private Date createTime = new Date();
//
//
//    /** 上线年限*/
//    private Long yearId = 0l;
//    /** 首页图片*/
//    private String showImage;
//    /** 描述*/
//    private String desp;
//    /** 评分*/
//    private float score = 0f;
//    /** 章节*/
//    private int chapter = 0;
//    /** 播放次数*/
//    private int playCount = 0;
//    /** 评论次数*/
//    private int commentCount = 0;
//    /** 点赞次数*/
//    private int goodCount = 0;
//    /** 收藏次数*/
//    private int collectionCount = 0;
//    /** 更新到多少章*/
//    private int nowChapter = 0;
//    /** 更新当前章节数章*/
//    private long nowChapterId = 0;
//    /** 内容标题*/
//    private String title="";
//
//    /** 搜索关键字*/
//    @JsonIgnore
//    private String keyword;
//
//    /** 上线状态 1 上线 0 不上线*/
//    @JsonIgnore
//    private int online = 1;
//
//    /** 资源类型   资源类型 1动画  2漫画 3壁纸*/
////	@JsonIgnore
//    private int resourceType = 1 ;
//
//    /** 是否付费*/
//    @JsonIgnore
//    private int isPay = 0;
//
//    /** 风格类型*/
//    @JsonIgnore
//    private int styleId =0;
//
//    /** 审核状态*/
//    @JsonIgnore
//    private Integer auditStatus=1 ;//审核状态  1 通过  0 不通过
//
//
//
//    /** 简介*/
//    private String briefDesp ;
//
//
//    /**排序字段*/
//    private int sortNum;
//
//
//    /**上线时间*/
//    @JsonIgnore
//    private Date onlineTime;
//
//    /**审核人*/
//    @JsonIgnore
//    private String auditBy;
//
//    /**审核时间*/
//    @JsonIgnore
//    private Date auditTime;
//
//    /**应用位置  观看选集*/
//    private String image_480_160;
//    private String image_296_296;
//    private String image_300_400;
//    private String image_800_450;
//    private String image_900_300;
//    private String image_480_126;
//
//    /**封面图原图*/
//    private String orijinalImage;
//
//
//
//    /**人气*/
//    private String popularity;
//    /**作品评分对应的描述*/
//    private String scoreDesp;
//    /**临时的栏目样式的图片的展示*/
//    private String imageUrl;
    //String lastTime   新加的 最新的动漫画更新时间



}
