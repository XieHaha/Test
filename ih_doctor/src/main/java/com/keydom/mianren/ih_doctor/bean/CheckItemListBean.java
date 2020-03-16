package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：检查列表对象
 * @Author：song
 * @Date：19/1/14 下午2:02
 * 修改人：xusong
 * 修改时间：19/1/14 下午2:02
 */
public class CheckItemListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String sex;
    private int age;
    private long deptId;
    private String deptName;
    private String hospitalName;
    private String doctor;
    private String applyTime;
    private String diagnosis;
    private String conditionDesc;
    private String outpatientNumber;
    private String specimenName;
    private List<CheckOutItemBean> items;
    private BigDecimal totalFee;
    private boolean edit;
    private boolean select = false;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getOutpatientNumber() {
        return outpatientNumber;
    }

    public void setOutpatientNumber(String outpatientNumber) {
        this.outpatientNumber = outpatientNumber;
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<CheckOutItemBean> getItems() {
        return items;
    }

    public void setItems(List<CheckOutItemBean> items) {
        this.items = items;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public CheckOutItemBean selectedItem() {
        if (items != null && items.size() > 0) {
            for (CheckOutItemBean item : items) {
                if (item.isSelect()) {
                    return item;
                }
            }
        }
        return null;
    }
}
