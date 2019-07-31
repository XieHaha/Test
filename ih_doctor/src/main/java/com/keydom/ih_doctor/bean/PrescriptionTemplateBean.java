package com.keydom.ih_doctor.bean;

public class PrescriptionTemplateBean {
    private boolean isSavedAsTemplate;
    private String modelNameTemp ;
    private String modelTypeTemp ;

    public boolean isSavedAsTemplate() {
        return isSavedAsTemplate;
    }

    public void setSavedAsTemplate(boolean savedAsTemplate) {
        isSavedAsTemplate = savedAsTemplate;
    }

    public String getModelNameTemp() {
        return modelNameTemp;
    }

    public void setModelNameTemp(String modelNameTemp) {
        this.modelNameTemp = modelNameTemp;
    }

    public String getModelTypeTemp() {
        return modelTypeTemp;
    }

    public void setModelTypeTemp(String modelTypeTemp) {
        this.modelTypeTemp = modelTypeTemp;
    }
}
