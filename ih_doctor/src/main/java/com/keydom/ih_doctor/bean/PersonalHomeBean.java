package com.keydom.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：个人信息对象
 * @Author：song
 * @Date：18/12/15 下午3:06
 * 修改人：xusong
 * 修改时间：18/12/15 下午3:06
 */
public class PersonalHomeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int currentOrder;
    private int totalOrder;
    private int currentComment;
    private int totalComment;
    private int isPerfectInfo;
    private String avatar;
    private String deptName;
    private String name;
    private String jobTitle;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getCurrentComment() {
        return currentComment;
    }

    public void setCurrentComment(int currentComment) {
        this.currentComment = currentComment;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getIsPerfectInfo() {
        return isPerfectInfo;
    }

    public void setIsPerfectInfo(int isPerfectInfo) {
        this.isPerfectInfo = isPerfectInfo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
