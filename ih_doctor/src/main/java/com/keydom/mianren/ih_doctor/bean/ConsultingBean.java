package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：门诊排班对象
 * @Author：song
 * @Date：18/11/28 上午9:42
 * 修改人：xusong
 * 修改时间：18/11/28 上午9:42
 */
public class ConsultingBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String week;
    private String beginPointTime;
    private String endPointTime;
    private String date;
    private Integer isStop;
    private String state;
    private String createTime;
    private String beginDate;
    private String endDate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getBeginPointTime() {
        return beginPointTime;
    }

    public void setBeginPointTime(String beginPointTime) {
        this.beginPointTime = beginPointTime;
    }

    public String getEndPointTime() {
        return endPointTime;
    }

    public void setEndPointTime(String endPointTime) {
        this.endPointTime = endPointTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }
}
