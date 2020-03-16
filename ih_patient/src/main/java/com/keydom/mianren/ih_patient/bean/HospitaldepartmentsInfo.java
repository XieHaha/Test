package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 医院科室
 */
public class HospitaldepartmentsInfo implements Serializable {

    @JSONField(name = "code")
    private String code;
    @JSONField(name = "fontColor")
    private String fontColor;
    @JSONField(name = "hospitalId")
    private String hospitalId;
    @JSONField(name = "image")
    private String image;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "hdList")
    private List<HdListBean> hdList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HdListBean> getHdList() {
        return hdList;
    }

    public void setHdList(List<HdListBean> hdList) {
        this.hdList = hdList;
    }

    public static class HdListBean implements Serializable {
        /**
         * abbreviation : asd
         * alpha :
         * deptCateId : 1
         * deptCode : neike
         * hospitalAreaId : 16
         * hospitalId : 3
         * id : 48
         * intro : asd
         * name : 科室三
         * priority : 1
         * remark :
         * state : 1
         */

        @JSONField(name = "abbreviation")
        private String abbreviation;
        @JSONField(name = "alpha")
        private String alpha;
        @JSONField(name = "deptCateId")
        private long deptCateId;
        @JSONField(name = "deptCode")
        private String deptCode;
        @JSONField(name = "hospitalAreaId")
        private long hospitalAreaId;
        @JSONField(name = "hospitalId")
        private long hospitalId;
        @JSONField(name = "id")
        private long id;
        @JSONField(name = "intro")
        private String intro;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "priority")
        private int priority;
        @JSONField(name = "remark")
        private String remark;
        @JSONField(name = "state")
        private int state;

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAlpha() {
            return alpha;
        }

        public void setAlpha(String alpha) {
            this.alpha = alpha;
        }

        public long getDeptCateId() {
            return deptCateId;
        }

        public void setDeptCateId(long deptCateId) {
            this.deptCateId = deptCateId;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public void setDeptCode(String deptCode) {
            this.deptCode = deptCode;
        }

        public long getHospitalAreaId() {
            return hospitalAreaId;
        }

        public void setHospitalAreaId(long hospitalAreaId) {
            this.hospitalAreaId = hospitalAreaId;
        }

        public long getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(long hospitalId) {
            this.hospitalId = hospitalId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
