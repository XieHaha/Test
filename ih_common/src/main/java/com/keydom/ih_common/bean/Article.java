package com.keydom.ih_common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author 赵参谋
 * @since 2018-11-19
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String hospitalId;

    private int articleType;

    private long submiterId;

    private String submiter;

    private String submitTime;

    private String auditResult;

    private String publishTime;

    private String title;

    private String summary;

    private String articleImage;
    private String jobTitle;

    private String content;

    private int collectionQuantity;

    private int readQuantity;

    private int likeQuantity;

    private int isStick;

    private int commentQuantity;

    private String doctorImage;

    private Integer isLike;

    private Integer isAttention;

    private Integer isCollect;

    private List<String> lableNames;

    private String hospitalName;

    private String deptName;

    private String cityName;

    private String userName;

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

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public long getSubmiterId() {
        return submiterId;
    }

    public void setSubmiterId(long submiterId) {
        this.submiterId = submiterId;
    }

    public String getSubmiter() {
        return submiter;
    }

    public void setSubmiter(String submiter) {
        this.submiter = submiter;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCollectionQuantity() {
        return collectionQuantity;
    }

    public void setCollectionQuantity(int collectionQuantity) {
        this.collectionQuantity = collectionQuantity;
    }

    public int getReadQuantity() {
        return readQuantity;
    }

    public void setReadQuantity(int readQuantity) {
        this.readQuantity = readQuantity;
    }

    public int getLikeQuantity() {
        return likeQuantity;
    }

    public void setLikeQuantity(int likeQuantity) {
        this.likeQuantity = likeQuantity;
    }

    public int getIsStick() {
        return isStick;
    }

    public void setIsStick(int isStick) {
        this.isStick = isStick;
    }

    public int getCommentQuantity() {
        return commentQuantity;
    }

    public void setCommentQuantity(int commentQuantity) {
        this.commentQuantity = commentQuantity;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public List<String> getLableNames() {
        return lableNames;
    }

    public void setLableNames(List<String> lableNames) {
        this.lableNames = lableNames;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
