package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 主页功能
 */
public class IndexFunction implements Serializable {
    private static final long serialVersionUID = 4885467120119438003L;

    public IndexFunction() {
    }

    public IndexFunction(int functionIcon, String name) {
        this.functionIcon = functionIcon;
        this.name = name;
    }

    public IndexFunction(int functionIcon, long id, String name) {
        this.functionIcon = functionIcon;
        this.id = id;
        this.name = name;
    }

    private int functionIcon;
    private boolean isSelected;
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name")
    private String name;

    private boolean isRedPointShow=false;

    public boolean isRedPointShow() {
        return isRedPointShow;
    }

    public void setRedPointShow(boolean redPointShow) {
        isRedPointShow = redPointShow;
    }

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

    @Override
    public String toString() {
        return "IndexFunction{" +
                "functionIcon=" + functionIcon +
                ", isSelected=" + isSelected +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
