package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class NoticeBean implements Serializable {

    /**
     * total : 16
     * size : 2
     * current : 1
     * records : [{"id":"256","hospitalId":"1078843924124262400","noticeType":0,"publisher":"超级管理员","publisherId":47,"submitTime":"2019-05-21 09:35:15","auditTime":"2019-05-21 09:35:15","auditer":"47","auditOpinion":"机构管理员发布医院级公告，不需要审核","auditResult":1,"state":1,"content":"123","remark":"","images":"","title":"123","isDel":0}]
     * pages : 8
     */

    @JSONField(name ="total")
    private String total;
    @JSONField(name ="size")
    private int size;
    @JSONField(name ="current")
    private int current;
    @JSONField(name ="pages")
    private String pages;
    @JSONField(name ="records")
    private List<Notice> records;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<Notice> getRecords() {
        return records;
    }

    public void setRecords(List<Notice> records) {
        this.records = records;
    }

    public static class Notice implements Serializable{
        /**
         * id : 256
         * hospitalId : 1078843924124262400
         * noticeType : 0
         * publisher : 超级管理员
         * publisherId : 47
         * submitTime : 2019-05-21 09:35:15
         * auditTime : 2019-05-21 09:35:15
         * auditer : 47
         * auditOpinion : 机构管理员发布医院级公告，不需要审核
         * auditResult : 1
         * state : 1
         * content : 123
         * remark : 
         * images : 
         * title : 123
         * isDel : 0
         */

        @JSONField(name ="id")
        private String id;
        @JSONField(name ="hospitalId")
        private String hospitalId;
        @JSONField(name ="noticeType")
        private int noticeType;
        @JSONField(name ="publisher")
        private String publisher;
        @JSONField(name ="publisherId")
        private int publisherId;
        @JSONField(name ="submitTime")
        private String submitTime;
        @JSONField(name ="auditTime")
        private String auditTime;
        @JSONField(name ="auditer")
        private String auditer;
        @JSONField(name ="auditOpinion")
        private String auditOpinion;
        @JSONField(name ="auditResult")
        private int auditResult;
        @JSONField(name ="state")
        private int state;
        @JSONField(name ="content")
        private String content;
        @JSONField(name ="remark")
        private String remark;
        @JSONField(name ="images")
        private String images;
        @JSONField(name ="title")
        private String title;
        @JSONField(name ="isDel")
        private int isDel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(int noticeType) {
            this.noticeType = noticeType;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public int getPublisherId() {
            return publisherId;
        }

        public void setPublisherId(int publisherId) {
            this.publisherId = publisherId;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(String auditTime) {
            this.auditTime = auditTime;
        }

        public String getAuditer() {
            return auditer;
        }

        public void setAuditer(String auditer) {
            this.auditer = auditer;
        }

        public String getAuditOpinion() {
            return auditOpinion;
        }

        public void setAuditOpinion(String auditOpinion) {
            this.auditOpinion = auditOpinion;
        }

        public int getAuditResult() {
            return auditResult;
        }

        public void setAuditResult(int auditResult) {
            this.auditResult = auditResult;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }
    }
}
