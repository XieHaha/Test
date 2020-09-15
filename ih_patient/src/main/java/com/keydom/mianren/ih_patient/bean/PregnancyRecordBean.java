package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

/**
 * des:产科病历记录
 *
 * @author 顿顿
 */
public class PregnancyRecordBean implements Serializable {

    private static final long serialVersionUID = 4934525201466240227L;

    /**
     * 检查日期
     */
    private String checkDate;
    /**
     * 孕周
     */
    private String pregnancyWeek;
    /**
     * 体重
     */
    private String weight;
    /**
     * 增加体重
     */
    private String addWeight;
    /**
     * 心率
     */
    private String heartRate;
    /**
     * 血压
     */
    private String bloodPressure;
    /**
     * 宫高
     */
    private String highPalace;
    /**
     * 腹围
     */
    private String abdominalGirth;
    /**
     * 胎心
     */
    private String fetalHeart;
    /**
     * 胎动
     */
    private String fetalMovement;
    /**
     * 妊娠分级 0绿色 1黄色 2橙色 3红色
     */
    private int level;
    /**
     * 水肿
     */
    private String edema;
    /**
     * 尿蛋白
     */
    private String urineProtein;
    /**
     * 其他
     */
    private String other;
    /**
     * 怀孕开始时间
     */
    private String pregnantStartDate;
    /**
     * 产检记录id
     */
    private String antepartumRecordId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 预产期
     */
    private String dueDate;
    /**
     * 是否传染病 1 是 0否
     */
    private int isInfectiousDiseases;

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getPregnancyWeek() {
        return pregnancyWeek;
    }

    public void setPregnancyWeek(String pregnancyWeek) {
        this.pregnancyWeek = pregnancyWeek;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAddWeight() {
        return addWeight;
    }

    public void setAddWeight(String addWeight) {
        this.addWeight = addWeight;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getHighPalace() {
        return highPalace;
    }

    public void setHighPalace(String highPalace) {
        this.highPalace = highPalace;
    }

    public String getAbdominalGirth() {
        return abdominalGirth;
    }

    public void setAbdominalGirth(String abdominalGirth) {
        this.abdominalGirth = abdominalGirth;
    }

    public String getFetalHeart() {
        return fetalHeart;
    }

    public void setFetalHeart(String fetalHeart) {
        this.fetalHeart = fetalHeart;
    }

    public String getFetalMovement() {
        return fetalMovement;
    }

    public void setFetalMovement(String fetalMovement) {
        this.fetalMovement = fetalMovement;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEdema() {
        return edema;
    }

    public void setEdema(String edema) {
        this.edema = edema;
    }

    public String getUrineProtein() {
        return urineProtein;
    }

    public void setUrineProtein(String urineProtein) {
        this.urineProtein = urineProtein;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPregnantStartDate() {
        return pregnantStartDate;
    }

    public void setPregnantStartDate(String pregnantStartDate) {
        this.pregnantStartDate = pregnantStartDate;
    }

    public String getAntepartumRecordId() {
        return antepartumRecordId;
    }

    public void setAntepartumRecordId(String antepartumRecordId) {
        this.antepartumRecordId = antepartumRecordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getIsInfectiousDiseases() {
        return isInfectiousDiseases;
    }

    public void setIsInfectiousDiseases(int isInfectiousDiseases) {
        this.isInfectiousDiseases = isInfectiousDiseases;
    }
}
