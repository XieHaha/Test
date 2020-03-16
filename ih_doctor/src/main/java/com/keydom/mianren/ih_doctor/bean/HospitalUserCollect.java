package com.keydom.mianren.ih_doctor.bean;
import java.io.Serializable;

/**
 * <p>
 * 我的收藏中间表
 * </p>
 *
 * @author ly
 * @since 2018-11-20 15点15分
 */
public class HospitalUserCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医护人员id
     */
    private Integer doctorId;
    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 是收藏  还是取消收藏 0:取消，1：收藏
     *
     * @return
     */
    private Integer isCollect;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    @Override
    public String toString() {
        return "HospitalUserCollect{" +
                ", doctorId=" + doctorId +
                ", articleId=" + articleId +
                "}";
    }
}
