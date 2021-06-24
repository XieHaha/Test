package com.keydom.mianren.ih_doctor.bean;

import java.util.ArrayList;

/**
 * created date: 2019/1/8 on 15:18
 * des:
 * author: HJW HP
 *
 * @author 顿顿
 */
public class MedicalRecordTempletBean {
    private long id;
    private String templateName;
    private String mainComplaint;
    private String historyIllness;
    private String historyAllergy;
    /**
     *  流行病学史
     */
    private String epidemicDiseaseHistory;
    /**
     * 发病日期
     */
    private String illnessDate;
    private String auxiliaryInspect;
    private String initDiagnosis;
    private String handleOpinion;
    private ArrayList<ICD10Bean> icdItems;

    public String getEpidemicDiseaseHistory() {
        return epidemicDiseaseHistory;
    }

    public void setEpidemicDiseaseHistory(String epidemicDiseaseHistory) {
        this.epidemicDiseaseHistory = epidemicDiseaseHistory;
    }

    public String getIllnessDate() {
        return illnessDate;
    }

    public void setIllnessDate(String illnessDate) {
        this.illnessDate = illnessDate;
    }

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

    public ArrayList<ICD10Bean> getIcdItems() {
        return icdItems;
    }

    public void setIcdItems(ArrayList<ICD10Bean> icdItems) {
        this.icdItems = icdItems;
    }
}
