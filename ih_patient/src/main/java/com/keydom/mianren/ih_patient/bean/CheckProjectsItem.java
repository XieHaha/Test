package com.keydom.mianren.ih_patient.bean;

import java.io.Serializable;

public class CheckProjectsItem  implements Serializable {
    private static final long serialVersionUID = 156332175968134172L;


    /**
     * isFasting : false
     * antepartumExamProjectName : 胎位检查、复查血常规、尿常规、宫高、腹围、胎心、血压、体重、胎心监测
     * inspectionTimesDesc : 第十次产检
     * timeDesc : 怀孕38周
     * inspectionTimes : 10
     * id : 10
     */

    private boolean isFasting;
    private String antepartumExamProjectName;
    private String inspectionTimesDesc;
    private String timeDesc;
    private long inspectionTimes;
    private long id;
    private boolean select = false;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isFasting() {
        return isFasting;
    }

    public void setFasting(boolean fasting) {
        isFasting = fasting;
    }

    public String getAntepartumExamProjectName() {
        return antepartumExamProjectName;
    }

    public void setAntepartumExamProjectName(String antepartumExamProjectName) {
        this.antepartumExamProjectName = antepartumExamProjectName;
    }

    public String getInspectionTimesDesc() {
        return inspectionTimesDesc;
    }

    public void setInspectionTimesDesc(String inspectionTimesDesc) {
        this.inspectionTimesDesc = inspectionTimesDesc;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public long getInspectionTimes() {
        return inspectionTimes;
    }

    public void setInspectionTimes(long inspectionTimes) {
        this.inspectionTimes = inspectionTimes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
