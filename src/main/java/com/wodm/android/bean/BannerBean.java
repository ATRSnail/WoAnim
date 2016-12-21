package com.wodm.android.bean;

import java.io.Serializable;

/**
 * android
 */
public class BannerBean implements Serializable {
            private int id;
            private String title;
            private int resourceId;
            private int sort;
            private String type;
            private String image;
            private String adsUrl;
            private String content;
            private int adType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getResourceId() {
                return resourceId;
            }

            public void setResourceId(int resourceId) {
                this.resourceId = resourceId;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getAdsUrl() {
                return adsUrl;
            }

            public void setAdsUrl(String adsUrl) {
                this.adsUrl = adsUrl;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAdType() {
                return adType;
            }

            public void setAdType(int adType) {
                this.adType = adType;
            }
}
