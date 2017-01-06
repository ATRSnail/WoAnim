package com.wodm.android.bean;

import java.util.List;

/**
 * Created by ATRSnail on 2017/1/3.
 * 分页获取章节的信息
 */

public class ChapterPageBean {

    /**
     * chapter/page
     请求的方式:GET
     请求的参数:
     resourceId:当前资源的id;
     page:当前第几页的内容;
     sortBy: 0表示已完结的 (根据章节正序) 1:表示未完结(根据章节倒序)
     请求返回的isNew:表示是不是最新的  1:表示是的 0:表示不是的;
     * code : 1000
     * message : 数据获取成功
     * pageNo : 0
     * pageSize : 16
     * totalPages : 1
     * data : [{"id":7859,"resourceId":553,"showImage":null,"desp":null,"title":"6","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/6?sign=aL3jbJfa6N0riCeZXgafLQ&t=1483431135","playCount":0,"chapter":6,"isNew":1},{"id":7861,"resourceId":553,"showImage":null,"desp":null,"title":"5","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/5?sign=VjD3RjyEH5OA7HIcnrclbw&t=1483431135","playCount":0,"chapter":5,"isNew":1},{"id":7862,"resourceId":553,"showImage":null,"desp":null,"title":"4","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/4?sign=79uxWqaUgus7YG5mlwTUUg&t=1483431135","playCount":0,"chapter":4,"isNew":1},{"id":7863,"resourceId":553,"showImage":null,"desp":null,"title":"3","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/3?sign=ijUMpIV-bG0CPI7Ko2F2FQ&t=1483431135","playCount":0,"chapter":3,"isNew":1},{"id":7860,"resourceId":553,"showImage":null,"desp":null,"title":"2","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/2?sign=DpAWzNDH-8XCMZjK4c-xrA&t=1483431135","playCount":0,"chapter":2,"isNew":1},{"id":7858,"resourceId":553,"showImage":null,"desp":null,"title":"1","contentUrl":"http://202.106.63.82/static/ftproot/ltcp03/m/manman/1?sign=wPwilMc0NYSpceuzqc8ebA&t=1483431135","playCount":0,"chapter":1,"isNew":1}]
     */

    private int code;
    private String message;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    /**
     * id : 7859
     * resourceId : 553
     * showImage : null
     * desp : null
     * title : 6
     * contentUrl : http://202.106.63.82/static/ftproot/ltcp03/m/manman/6?sign=aL3jbJfa6N0riCeZXgafLQ&t=1483431135
     * playCount : 0
     * chapter : 6
     * isNew : 1
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private int resourceId;
        private Object showImage;
        private Object desp;
        private String title;
        private String contentUrl;
        private int playCount;
        private int chapter;
        private int isNew;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getResourceId() {
            return resourceId;
        }

        public void setResourceId(int resourceId) {
            this.resourceId = resourceId;
        }

        public Object getShowImage() {
            return showImage;
        }

        public void setShowImage(Object showImage) {
            this.showImage = showImage;
        }

        public Object getDesp() {
            return desp;
        }

        public void setDesp(Object desp) {
            this.desp = desp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }
    }
}
