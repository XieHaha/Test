package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

/**
 * @date 20/6/8 17:10
 * @des 药品用法
 */
public class UseWayBean implements Serializable {
    private static final long serialVersionUID = -2516208504475802748L;
    private String id;
    private String priority;
    private String code;
    private String codeName;
    private String codeValue;
    private String codeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }
}
