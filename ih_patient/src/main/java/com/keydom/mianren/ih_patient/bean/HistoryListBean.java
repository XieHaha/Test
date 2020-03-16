package com.keydom.mianren.ih_patient.bean;

/**
 * created date: 2018/12/15 on 14:19
 * des:既往病史实体
 */
public class HistoryListBean {

    /**
     * id : 1
     * marriageChildbearingHistory : 已婚,未婚,离异,丧偶,未生育,备孕期,怀孕期,已生育
     * historySurgeryOrTrauma : 无,颅脑手术,颈部手术,四肢手术,胸部手术,背部手术,骨折,头部外伤,烧伤
     * familyHistory : 无,高血压,脑梗,心脏病,糖尿病,脑出血,癫痫病,过敏性疾病,白癜风
     * personalHistory : 吸烟,常饮酒,久坐,熬夜
     * allergyHistory : 无,迪卡因,头孢类抗生素,青霉素,阿司匹林,普鲁卡因
     */

    private long id;
    private String marriageChildbearingHistory;
    private String historySurgeryOrTrauma;
    private String familyHistory;
    private String personalHistory;
    private String allergyHistory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMarriageChildbearingHistory() {
        return marriageChildbearingHistory;
    }

    public void setMarriageChildbearingHistory(String marriageChildbearingHistory) {
        this.marriageChildbearingHistory = marriageChildbearingHistory;
    }

    public String getHistorySurgeryOrTrauma() {
        return historySurgeryOrTrauma;
    }

    public void setHistorySurgeryOrTrauma(String historySurgeryOrTrauma) {
        this.historySurgeryOrTrauma = historySurgeryOrTrauma;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }
}
