package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：评价对象
 * @Author：song
 * @Date：18/12/17 下午6:58
 * 修改人：xusong
 * 修改时间：18/12/17 下午6:58
 */
public class EvaluationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int grade;
    private List<String> labels;
    /**
     * id : 1184028963064811521
     * likeAmount : null
     * avatar : group1/M00/00/26/rBAA0l2lOLuABSoXAAFmXHNRwXI485.jpg
     * isLike : null
     * time : null
     */

    private String id;
    private Integer likeAmount;
    private String avatar;
    private Integer isLike;
    private String time;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
