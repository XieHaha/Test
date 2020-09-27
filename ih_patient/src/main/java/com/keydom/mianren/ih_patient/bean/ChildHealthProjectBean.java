package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 顿顿
 * @date 20/9/27 10:40
 * @des
 */
public class ChildHealthProjectBean implements Serializable {
    private static final long serialVersionUID = -1120378497804764628L;

    private int id;
    private int age;
    private String  attention;
    private List<ChildHealthProjectItemBean> mustFill;
    private List<ChildHealthProjectItemBean> notMustFill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<ChildHealthProjectItemBean> getMustFill() {
        return mustFill;
    }

    public void setMustFill(List<ChildHealthProjectItemBean> mustFill) {
        this.mustFill = mustFill;
    }

    public List<ChildHealthProjectItemBean> getNotMustFill() {
        return notMustFill;
    }

    public void setNotMustFill(List<ChildHealthProjectItemBean> notMustFill) {
        this.notMustFill = notMustFill;
    }
}
