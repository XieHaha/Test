package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * created date: 2018/12/27 on 11:26
 * des:订单评价实体
 */
public class OrderEvaluateBean implements Serializable{
    public static final String nursing_order_title = "护理评价";
    public static final String subscribe_exam_order_title = "体检预约评价";

    private String title;
    private String type;
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
