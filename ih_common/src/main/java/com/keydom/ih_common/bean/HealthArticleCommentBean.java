package com.keydom.ih_common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class HealthArticleCommentBean {



    @JSONField(name = "id")
    private long id;
    @JSONField(name = "healthKnowledgeId")
    private long articleId;
    @JSONField(name = "criticsId")
    private long criticsId;
    @JSONField(name = "criticsName")
    private String criticsName;
    @JSONField(name = "criticsImage")
    private String criticsImage;
    @JSONField(name = "byCriticsName")
    private String byCriticsName;
    @JSONField(name = "byCommentsContext")
    private String byCommentsContext;
    @JSONField(name = "myCommentContexxt")
    private String myCommentContexxt;
    @JSONField(name = "commentLike")
    private int commentLike;
    @JSONField(name = "commentTime")
    private Date commentTime;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "isLike")
    private Integer isLike;
    /**
     * commentTime : 2018-12-06 09:29:01
     * remark : null
     * isLike : null
     * registerUserId : null
     * iscLick : null
     */
    @JSONField(name = "registerUserId")
    private int registerUserId;
    @JSONField(name = "iscLick")
    private int iscLick;

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getCriticsId() {
        return criticsId;
    }

    public void setCriticsId(long criticsId) {
        this.criticsId = criticsId;
    }

    public String getCriticsName() {
        return criticsName;
    }

    public void setCriticsName(String criticsName) {
        this.criticsName = criticsName;
    }

    public String getCriticsImage() {
        return criticsImage;
    }

    public void setCriticsImage(String criticsImage) {
        this.criticsImage = criticsImage;
    }

    public String getByCriticsName() {
        return byCriticsName;
    }

    public void setByCriticsName(String byCriticsName) {
        this.byCriticsName = byCriticsName;
    }

    public String getByCommentsContext() {
        return byCommentsContext;
    }

    public void setByCommentsContext(String byCommentsContext) {
        this.byCommentsContext = byCommentsContext;
    }

    public String getMyCommentContexxt() {
        return myCommentContexxt;
    }

    public void setMyCommentContexxt(String myCommentContexxt) {
        this.myCommentContexxt = myCommentContexxt;
    }

    public int getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(int commentLike) {
        this.commentLike = commentLike;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(int registerUserId) {
        this.registerUserId = registerUserId;
    }

    public int getIscLick() {
        return iscLick;
    }

    public void setIscLick(int iscLick) {
        this.iscLick = iscLick;
    }
}
