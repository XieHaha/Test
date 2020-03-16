package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：我的收入对象
 * @Author：song
 * @Date：18/12/13 下午5:07
 * 修改人：xusong
 * 修改时间：18/12/13 下午5:07
 */
public class MyIncomeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private double monthIncome;
    private double totalIncome;
    private String currentDate;
    private int totalOrderNumber;
    private int imageOrderNumber;
    private int videoOrderNumber;
    private int telOrderNumber;
    private List<MonthIncomeBean> list;

    public List<MonthIncomeBean> getList() {
        return list;
    }

    public void setList(List<MonthIncomeBean> list) {
        this.list = list;
    }

    public double getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(double monthIncome) {
        this.monthIncome = monthIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public int getTotalOrderNumber() {
        return totalOrderNumber;
    }

    public void setTotalOrderNumber(int totalOrderNumber) {
        this.totalOrderNumber = totalOrderNumber;
    }

    public int getImageOrderNumber() {
        return imageOrderNumber;
    }

    public void setImageOrderNumber(int imageOrderNumber) {
        this.imageOrderNumber = imageOrderNumber;
    }

    public int getVideoOrderNumber() {
        return videoOrderNumber;
    }

    public void setVideoOrderNumber(int videoOrderNumber) {
        this.videoOrderNumber = videoOrderNumber;
    }

    public int getTelOrderNumber() {
        return telOrderNumber;
    }

    public void setTelOrderNumber(int telOrderNumber) {
        this.telOrderNumber = telOrderNumber;
    }
}
