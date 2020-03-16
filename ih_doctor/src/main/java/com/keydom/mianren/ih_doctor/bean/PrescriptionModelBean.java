package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;

public class PrescriptionModelBean implements Serializable {
    private String modelName;
    private String modelType;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
