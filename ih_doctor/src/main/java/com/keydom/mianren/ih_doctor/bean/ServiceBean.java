package com.keydom.mianren.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;


public class ServiceBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String code;
    private int state;
    private List<DoctorServiceSubVoBean> doctorServiceSubVoList;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DoctorServiceSubVoBean> getDoctorServiceSubVoList() {
        return doctorServiceSubVoList;
    }

    public void setDoctorServiceSubVoList(List<DoctorServiceSubVoBean> doctorServiceSubVoList) {
        this.doctorServiceSubVoList = doctorServiceSubVoList;
    }

    public class DoctorServiceSubVoBean implements Serializable{
        private String subId;
        private String subName;
        private String subCode;
        private int subState;

        public String getSubId() {
            return subId;
        }

        public void setSubId(String subId) {
            this.subId = subId;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getSubCode() {
            return subCode;
        }

        public void setSubCode(String subCode) {
            this.subCode = subCode;
        }

        public int getSubState() {
            return subState;
        }

        public void setSubState(int subState) {
            this.subState = subState;
        }
    }
}
