package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * @author 顿顿
 * @date 20/9/27 10:40
 * @des
 */
public class ChildHealthProjectItemBean implements Serializable {
    private static final long serialVersionUID = -1120378497804764628L;
    private int isMustFill;
    private double price;
    private String name;

    public int getIsMustFill() {
        return isMustFill;
    }

    public void setIsMustFill(int isMustFill) {
        this.isMustFill = isMustFill;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
