package com.keydom.ih_common.bean;

/**
 * @Name：com.keydom.ih_common.bean
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/10 下午1:47
 * 修改人：xusong
 * 修改时间：18/12/10 下午1:47
 */
public class ArticleLikeUserBean {

    private long doctorId;
    private String doctorImage;
    private long articleId;

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
