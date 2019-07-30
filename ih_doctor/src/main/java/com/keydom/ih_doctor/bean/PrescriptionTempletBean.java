package com.keydom.ih_doctor.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 处方模板实体类
 */
public class PrescriptionTempletBean {

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "templateName")
    private String templateName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
