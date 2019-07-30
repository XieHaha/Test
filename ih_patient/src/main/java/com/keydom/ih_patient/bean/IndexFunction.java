package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 主页功能
 */
public class IndexFunction implements Serializable {
    private static final long serialVersionUID = 4885467120119438003L;
    private int functionIcon;
    private boolean isSelected;
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name")
    private String name;


    public int getFunctionIcon() {
        return functionIcon;
    }

    public void setFunctionIcon(int functionIcon) {
        this.functionIcon = functionIcon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
