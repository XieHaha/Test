package com.keydom.ih_doctor.bean;

/**
 * created date: 2019/1/8 on 15:18
 * des:
 * author: HJW HP
 */
public class MedicalRecordTempletBean {
    private long id;
    private String templateName;
    private String mainComplaint;
    private String historyIllness;
    private String historyAllergy;
    private String auxiliaryInspect;
    private String initDiagnosis;

    public String getMainComplaint() {
        return mainComplaint;
    }

    public void setMainComplaint(String mainComplaint) {
        this.mainComplaint = mainComplaint;
    }

    public String getHistoryIllness() {
        return historyIllness;
    }

    public void setHistoryIllness(String historyIllness) {
        this.historyIllness = historyIllness;
    }

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getAuxiliaryInspect() {
        return auxiliaryInspect;
    }

    public void setAuxiliaryInspect(String auxiliaryInspect) {
        this.auxiliaryInspect = auxiliaryInspect;
    }

    public String getInitDiagnosis() {
        return initDiagnosis;
    }

    public void setInitDiagnosis(String initDiagnosis) {
        this.initDiagnosis = initDiagnosis;
    }

    public String getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(String handleOpinion) {
        this.handleOpinion = handleOpinion;
    }

    private String handleOpinion

            ;

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
