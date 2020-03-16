package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * created date: 2019/1/10 on 9:57
 * des: 医生基础信息
 */
public class DoctorMainBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * imageFee : 30
     * videoFee : 50
     * teamMembers : []
     * info : {"uuid":"00001C00002","avatar":"","name":"测试科室0","hospitalName":"人民医院","deptName":"眼科","schedu":null,"jobTitle":null,"qrcode":"?userCode=00001C00002&hospitalId=3&deptId=66","intro":"","adept":"","inquisitionAmount":0,"feedbackRate":"100.0%","averageResponseTime":0,"attentionAmount":0,"isInquiry":0,"waitInquiryCount":0}
     */

    private String imageFee;
    private String videoFee;
    private InfoBean info;
    private List<DoctorTeamItem> teamMembers;

    public String getImageFee() {
        return imageFee;
    }

    public void setImageFee(String imageFee) {
        this.imageFee = imageFee;
    }

    public String getVideoFee() {
        return videoFee;
    }

    public void setVideoFee(String videoFee) {
        this.videoFee = videoFee;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<DoctorTeamItem> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<DoctorTeamItem> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public static class InfoBean implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * uuid : 00001C00002
         * avatar :
         * name : 测试科室0
         * hospitalName : 人民医院
         * deptName : 眼科
         * schedu : null
         * jobTitle : null
         * qrcode : ?userCode=00001C00002&hospitalId=3&deptId=66
         * intro :
         * adept :
         * inquisitionAmount : 0
         * feedbackRate : 100.0%
         * averageResponseTime : 0
         * attentionAmount : 0
         * isInquiry : 0
         * waitInquiryCount : 0
         */

        private String uuid;
        private String avatar;
        private String name;
        private String hospitalName;
        private String deptName;
        private String schedu;
        private String jobTitle;
        private String qrcode;
        private String intro;
        private String adept;
        private int inquisitionAmount;
        private String feedbackRate;
        private int averageResponseTime;
        private int attentionAmount;
        private int isInquiry;
        private int isDoctor;
        private int isEnabledImage;
        private int isEnabledVideo;
        private int waitInquiryCount;
        private int isAttention;
        private float discount;

        public int getIsDoctor() {
            return isDoctor;
        }

        public void setIsDoctor(int isDoctor) {
            this.isDoctor = isDoctor;
        }

        public float getDiscount() {
            return discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }

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

        public int getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(int isAttention) {
            this.isAttention = isAttention;
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
}
