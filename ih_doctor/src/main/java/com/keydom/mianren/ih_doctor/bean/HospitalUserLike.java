package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * 点赞中间表
 *
 * @author ly
 * @since 2018-11-21 09点53分
 */
public class HospitalUserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医护人员id
     */
    private Integer doctorId;
    /**
     * 医护人员头像
     */
    private String doctorImage;
    /**
     * 文章id
     */
    private Integer articleId;


    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "HospitalUserLike{" +
        ", doctorId=" + doctorId +
        ", doctorImage=" + doctorImage +
        ", articleId=" + articleId +
        "}";
    }
}
