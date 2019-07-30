package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 科室医生搜索
 */
public class DocAndDepSearchBean {
    @JSONField(name = "EsHospitalUser")
    private List<DoctorInfo> EsHospitalUser;

    public List<DoctorInfo> getEsHospitalUser() {
        return EsHospitalUser;
    }

    public void setEsHospitalUser(List<DoctorInfo> EsHospitalUser) {
        this.EsHospitalUser = EsHospitalUser;
    }
    @JSONField(name = "HospitalDept")
    private List<DepartmentInfo> HospitalDept;

    public List<DepartmentInfo> getHospitalDept() {
        return HospitalDept;
    }

    public void setHospitalDept(List<DepartmentInfo> hospitalDept) {
        HospitalDept = hospitalDept;
    }


}
