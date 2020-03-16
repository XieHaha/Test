package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 主页index
 */
public class IndexData {


    @JSONField(name = "advertisement")
    private List<AdvertisementBean> advertisement;
    @JSONField(name = "healthKnowledges")
    private List<HealthKnowledgesBean> healthKnowledges;
    @JSONField(name = "headerbanner")
    private List<HeaderbannerBean> headerbanner;
    @JSONField(name = "notifications")
    private List<NotificationsBean> notifications;
    @JSONField(name = "unFinishInquiry")
    private int unFinishInquiry;
    @JSONField(name = "unFinishNurse")
    private int unFinishNurse;
    @JSONField(name = "clinic")
    private int clinic;
    @JSONField(name = "unFinishAdmission")
    private int unFinishAdmission;
    @JSONField(name = "unFinishInspect")
    private int unFinishInspect;

    public int getUnFinishInquiry() {
        return unFinishInquiry;
    }

    public void setUnFinishInquiry(int unFinishInquiry) {
        this.unFinishInquiry = unFinishInquiry;
    }

    public int getUnFinishNurse() {
        return unFinishNurse;
    }

    public void setUnFinishNurse(int unFinishNurse) {
        this.unFinishNurse = unFinishNurse;
    }

    public int getClinic() {
        return clinic;
    }

    public void setClinic(int clinic) {
        this.clinic = clinic;
    }

    public int getUnFinishAdmission() {
        return unFinishAdmission;
    }

    public void setUnFinishAdmission(int unFinishAdmission) {
        this.unFinishAdmission = unFinishAdmission;
    }

    public int getUnFinishInspect() {
        return unFinishInspect;
    }

    public void setUnFinishInspect(int unFinishInspect) {
        this.unFinishInspect = unFinishInspect;
    }

    public List<AdvertisementBean> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(List<AdvertisementBean> advertisement) {
        this.advertisement = advertisement;
    }

    public List<HealthKnowledgesBean> getHealthKnowledges() {
        return healthKnowledges;
    }

    public void setHealthKnowledges(List<HealthKnowledgesBean> healthKnowledges) {
        this.healthKnowledges = healthKnowledges;
    }

    public List<HeaderbannerBean> getHeaderbanner() {
        return headerbanner;
    }

    public void setHeaderbanner(List<HeaderbannerBean> headerbanner) {
        this.headerbanner = headerbanner;
    }

    public List<NotificationsBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsBean> notifications) {
        this.notifications = notifications;
    }

    public static class AdvertisementBean {

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "pictureName")
        private String pictureName;
        @JSONField(name = "picturePath")
        private String picturePath;
        @JSONField(name = "pictureUrl")
        private String pictureUrl;
        @JSONField(name = "status")
        private String status;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPictureName() {
            return pictureName;
        }

        public void setPictureName(String pictureName) {
            this.pictureName = pictureName;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class HealthKnowledgesBean {

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "hospitalId")
        private String hospitalId;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "summary")
        private String summary;
        @JSONField(name = "content")
        private String content;
        @JSONField(name = "creatorCode")
        private String creatorCode;
        @JSONField(name = "createTime")
        private String createTime;
        @JSONField(name = "pageView")
        private int pageView;
        @JSONField(name = "remark")
        private String remark;
        @JSONField(name = "imageList")
        private List<String> imageList;
        @JSONField(name = "lablelist")
        private List<String> lablelist;
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public List<String> getImageList() {
            return imageList;
        }

        public void setImageList(List<String> imageList) {
            this.imageList = imageList;
        }

        public List<String> getLablelist() {
            return lablelist;
        }

        public void setLablelist(List<String> lablelist) {
            this.lablelist = lablelist;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatorCode() {
            return creatorCode;
        }

        public void setCreatorCode(String creatorCode) {
            this.creatorCode = creatorCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPageView() {
            return pageView;
        }

        public void setPageView(int pageView) {
            this.pageView = pageView;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class HeaderbannerBean {

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "pictureName")
        private String pictureName;
        @JSONField(name = "picturePath")
        private String picturePath;
        @JSONField(name = "pictureUrl")
        private String pictureUrl;
        @JSONField(name = "status")
        private String status;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPictureName() {
            return pictureName;
        }

        public void setPictureName(String pictureName) {
            this.pictureName = pictureName;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class NotificationsBean implements Serializable {

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "hospitalId")
        private String hospitalId;
        @JSONField(name = "noticeType")
        private int noticeType;
        @JSONField(name = "publisher")
        private String publisher;
        @JSONField(name = "submitTime")
        private String submitTime;
        @JSONField(name = "auditTime")
        private String auditTime;
        @JSONField(name = "auditer")
        private String auditer;
        @JSONField(name = "auditOpinion")
        private String auditOpinion;
        @JSONField(name = "auditResult")
        private String auditResult;
        @JSONField(name = "state")
        private int state;
        @JSONField(name = "content")
        private String content;
        @JSONField(name = "remark")
        private String remark;
        @JSONField(name = "images")
        private String images;
        @JSONField(name = "title")
        private String title;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
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

        public String getAuditResult() {
            return auditResult;
        }

        public void setAuditResult(String auditResult) {
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
    }
}
