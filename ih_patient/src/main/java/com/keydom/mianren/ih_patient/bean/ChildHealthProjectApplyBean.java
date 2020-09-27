package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/27 10:40
 * @des
 */
public class ChildHealthProjectApplyBean implements Serializable {
    private static final long serialVersionUID = 5161150339930271838L;
    private double price;
    private double fee;
    private int age;
    private String childProjectName;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getChildProjectName() {
        return childProjectName;
    }

    public void setChildProjectName(String childProjectName) {
        this.childProjectName = childProjectName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
