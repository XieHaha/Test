package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @date 20/3/6 15:05
 * @des 挂号预约选择科室返回数据  多个数据封装
 */
public class ReserveSelectDepartBean implements Serializable {
    private static final long serialVersionUID = -2963916883294155828L;

    //科室数据
    private String secondDepartmentName;
    private long id;

    //院区数据
    private long selectedAreaId;
    private String selectedAreaName;

    //科室列表数据
    private List<HospitaldepartmentsInfo> departmentList ;


    public String getSecondDepartmentName() {
        return secondDepartmentName;
    }

    public void setSecondDepartmentName(String secondDepartmentName) {
        this.secondDepartmentName = secondDepartmentName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSelectedAreaId() {
        return selectedAreaId;
    }

    public void setSelectedAreaId(long selectedAreaId) {
        this.selectedAreaId = selectedAreaId;
    }

    public String getSelectedAreaName() {
        return selectedAreaName;
    }

    public void setSelectedAreaName(String selectedAreaName) {
        this.selectedAreaName = selectedAreaName;
    }

    public List<HospitaldepartmentsInfo> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<HospitaldepartmentsInfo> departmentList) {
        this.departmentList = departmentList;
    }
}
