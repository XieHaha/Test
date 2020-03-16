package com.keydom.mianren.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 检查报告对象类
 */
public class BodyCheckRecordInfo {
    @JSONField(name = "date")
    private String date;
    @JSONField(name = "inspectRecordVOList")
    private List<InspectRecordVOListBean> inspectRecordVOList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<InspectRecordVOListBean> getInspectRecordVOList() {
        return inspectRecordVOList;
    }

    public void setInspectRecordVOList(List<InspectRecordVOListBean> inspectRecordVOList) {
        this.inspectRecordVOList = inspectRecordVOList;
    }

    public static class InspectRecordVOListBean {
        /**
         * applyNumber : 2
         * partientNumber : 2
         * inspectCode : 1
         * inspectName :
         * inspectTProjectId : 1
         */

        @JSONField(name = "applyNumber")
        private String applyNumber;
        @JSONField(name = "partientNumber")
        private String partientNumber;
        @JSONField(name = "inspectCode")
        private long inspectCode;
        @JSONField(name = "inspectName")
        private String inspectName;
        @JSONField(name = "inspectTProjectId")
        private long inspectTProjectId;

        public String getApplyNumber() {
            return applyNumber;
        }

        public void setApplyNumber(String applyNumber) {
            this.applyNumber = applyNumber;
        }

        public String getPartientNumber() {
            return partientNumber;
        }

        public void setPartientNumber(String partientNumber) {
            this.partientNumber = partientNumber;
        }

        public long getInspectCode() {
            return inspectCode;
        }

        public void setInspectCode(long inspectCode) {
            this.inspectCode = inspectCode;
        }

        public String getInspectName() {
            return inspectName;
        }

        public void setInspectName(String inspectName) {
            this.inspectName = inspectName;
        }

        public long getInspectTProjectId() {
            return inspectTProjectId;
        }

        public void setInspectTProjectId(long inspectTProjectId) {
            this.inspectTProjectId = inspectTProjectId;
        }
    }
}
