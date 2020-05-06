package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/5/6 14:57
 * @des 会诊意见
 */
public class ConsultationAdviceBean implements Serializable {
    private static final long serialVersionUID = -8163150149358718538L;

    private int type;
    private String doctorName;
    private String deptName;
    private String createTime;
    private String audioUrl;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
