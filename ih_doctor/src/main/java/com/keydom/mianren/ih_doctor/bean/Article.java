package com.keydom.mianren.ih_doctor.bean;

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

    private int collectionQuantity;

    private int readQuantity;

    private int likeQuantity;

    private int isStick;

    private int commentQuantity;

    private String doctorImage;

    private String isLike;

    private int isAttention;

    private int isCollect;

    private List<String> lableNames;

    private String hospitalName;

    private String deptName;

    private String cityName;

    private String userName;

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

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
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
