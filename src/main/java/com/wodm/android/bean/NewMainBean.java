package com.wodm.android.bean;

import java.util.List;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMainBean {


        private int id;
        private String name;
        private Object icon;
        private int type;
        private int style;

        private List<ResourcesBean> resources;

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

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public List<ResourcesBean> getResources() {
            return resources;
        }

        public void setResources(List<ResourcesBean> resources) {
            this.resources = resources;
        }

        public static class ResourcesBean {
            private int id;
            private String name;
            private int categoryId;
            private int copyrightId;
            private int type;
            private int areaId;
            private String author;
            private int yearId;
            private String imageUrl;
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
            private int sortNum;
            private String image_480_126;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            private int sort;

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

            public int getSortNum() {
                return sortNum;
            }

            public void setSortNum(int sortNum) {
                this.sortNum = sortNum;
            }

            public String getImage_480_126() {
                return image_480_126;
            }

            public void setImage_480_126(String image_480_126) {
                this.image_480_126 = image_480_126;
            }
            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }
        }
}
