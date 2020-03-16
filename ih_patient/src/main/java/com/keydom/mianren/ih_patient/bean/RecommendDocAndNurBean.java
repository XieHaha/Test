package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 关注医生和护士
 */
public class RecommendDocAndNurBean {
    @JSONField(name = "uuid")
    private String uuid;
    @JSONField(name = "avatar")
    private String avatar;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "deptName")
    private String deptName;
    @JSONField(name = "schedu")
    private String schedu;
    @JSONField(name = "jobTitle")
    private String jobTitle;
    @JSONField(name = "qrcode")
    private String qrcode;
    @JSONField(name = "intro")
    private String intro;
    @JSONField(name = "adept")
    private String adept;
    @JSONField(name = "inquisitionAmount")
    private int inquisitionAmount;
    @JSONField(name = "feedbackRate")
    private String feedbackRate;
    @JSONField(name = "averageResponseTime")
    private int averageResponseTime;
    @JSONField(name = "attentionAmount")
    private int attentionAmount;
    @JSONField(name = "isInquiry")
    private int isInquiry;
    @JSONField(name = "waitInquiryCount")
    private int waitInquiryCount;
    @JSONField(name = "isEnabledImage")
    private int isEnabledImage;
    @JSONField(name = "isEnabledVideo")
    private int isEnabledVideo;

    public int getIsEnabledImage() {
        return isEnabledImage;
    }

    public void setIsEnabledImage(int isEnabledImage) {
        this.isEnabledImage = isEnabledImage;
    }

    public int getIsEnabledVideo() {
        return isEnabledVideo;
    }

    public void setIsEnabledVideo(int isEnabledVideo) {
        this.isEnabledVideo = isEnabledVideo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSchedu() {
        return schedu;
    }

    public void setSchedu(String schedu) {
        this.schedu = schedu;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAdept() {
        return adept;
    }

    public void setAdept(String adept) {
        this.adept = adept;
    }

    public int getInquisitionAmount() {
        return inquisitionAmount;
    }

    public void setInquisitionAmount(int inquisitionAmount) {
        this.inquisitionAmount = inquisitionAmount;
    }

    public String getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(String feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public int getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(int averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public int getAttentionAmount() {
        return attentionAmount;
    }

    public void setAttentionAmount(int attentionAmount) {
        this.attentionAmount = attentionAmount;
    }

    public int getIsInquiry() {
        return isInquiry;
    }

    public void setIsInquiry(int isInquiry) {
        this.isInquiry = isInquiry;
    }

    public int getWaitInquiryCount() {
        return waitInquiryCount;
    }

    public void setWaitInquiryCount(int waitInquiryCount) {
        this.waitInquiryCount = waitInquiryCount;
    }
}
