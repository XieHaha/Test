package com.keydom.ih_common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class HealthArticalInfo {

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "hospitalCode")
    private String hospitalCode;
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
    @JSONField(name = "collectionQuantity")
    private int collectionQuantity;
    @JSONField(name = "likeQuantity")
    private String likeQuantity;
    @JSONField(name = "isStick")
    private String isStick;
    @JSONField(name = "commentQuantity")
    private int commentQuantity;
    @JSONField(name = "lablelist")
    private List<String> lablelist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
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

    public int getCollectionQuantity() {
        return collectionQuantity;
    }

    public void setCollectionQuantity(int collectionQuantity) {
        this.collectionQuantity = collectionQuantity;
    }

    public String getLikeQuantity() {
        return likeQuantity;
    }

    public void setLikeQuantity(String likeQuantity) {
        this.likeQuantity = likeQuantity;
    }

    public String getIsStick() {
        return isStick;
    }

    public void setIsStick(String isStick) {
        this.isStick = isStick;
    }

    public int getCommentQuantity() {
        return commentQuantity;
    }

    public void setCommentQuantity(int commentQuantity) {
        this.commentQuantity = commentQuantity;
    }

    public List<String> getLablelist() {
        return lablelist;
    }

    public void setLablelist(List<String> lablelist) {
        this.lablelist = lablelist;
    }
}
