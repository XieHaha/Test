package com.keydom.ih_doctor.bean;

import java.io.Serializable;

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
    private long id;
    private String name;
    private int grade;
    private String[] labels;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }
}
