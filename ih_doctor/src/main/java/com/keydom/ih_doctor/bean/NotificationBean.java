package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：通知公告对象
 * @Author：song
 * @Date：18/11/27 下午3:57
 * 修改人：xusong
 * 修改时间：18/11/27 下午3:57
 */
public class NotificationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer noticeType;
    private String publisher;
    private String submitTime;
    private String content;
    private String title;
    private String auditOpinion;
    private int auditResult;
    private boolean mine;

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(int auditResult) {
        this.auditResult = auditResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
