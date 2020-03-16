package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 体检套餐实体
 */
public class PhysicalExaCommentsInfo {
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "medicalReservationId")
    private long medicalReservationId;
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
    private long commentLike;
    @JSONField(name = "commentTime")
    private String commentTime;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "isLike")
    private int isLike;
    @JSONField(name = "registerUserId")
    private long registerUserId;
    @JSONField(name = "iscLick")
    private int iscLick;
    @JSONField(name = "ibyCriticsId")
    private long ibyCriticsId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMedicalReservationId() {
        return medicalReservationId;
    }

    public void setMedicalReservationId(long medicalReservationId) {
        this.medicalReservationId = medicalReservationId;
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

    public long getCommentLike() {
        return commentLike;
    }

    public void setCommentLike(long commentLike) {
        this.commentLike = commentLike;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public int getIscLick() {
        return iscLick;
    }

    public void setIscLick(int iscLick) {
        this.iscLick = iscLick;
    }

    public long getIbyCriticsId() {
        return ibyCriticsId;
    }

    public void setIbyCriticsId(long ibyCriticsId) {
        this.ibyCriticsId = ibyCriticsId;
    }
}
