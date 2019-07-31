package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.List;

/**
 * 护理服务角标
 */
public class NursingIndexInfo {


    @JSONField(name = "patientHomePageWaitDealServiceDto")
    private PatientHomePageWaitDealServiceDtoBean patientHomePageWaitDealServiceDto;
    @JSONField(name = "nurseCategoryIdAndNameDtos")
    private List<NurseCategoryIdAndNameDtosBean> nurseCategoryIdAndNameDtos;
    @JSONField(name = "nurseProjectDetailDtos")
    private List<NurseProjectDetailDtosBean> nurseProjectDetailDtos;

    public PatientHomePageWaitDealServiceDtoBean getPatientHomePageWaitDealServiceDto() {
        return patientHomePageWaitDealServiceDto;
    }

    public void setPatientHomePageWaitDealServiceDto(PatientHomePageWaitDealServiceDtoBean patientHomePageWaitDealServiceDto) {
        this.patientHomePageWaitDealServiceDto = patientHomePageWaitDealServiceDto;
    }

    public List<NurseCategoryIdAndNameDtosBean> getNurseCategoryIdAndNameDtos() {
        return nurseCategoryIdAndNameDtos;
    }

    public void setNurseCategoryIdAndNameDtos(List<NurseCategoryIdAndNameDtosBean> nurseCategoryIdAndNameDtos) {
        this.nurseCategoryIdAndNameDtos = nurseCategoryIdAndNameDtos;
    }

    public List<NurseProjectDetailDtosBean> getNurseProjectDetailDtos() {
        return nurseProjectDetailDtos;
    }

    public void setNurseProjectDetailDtos(List<NurseProjectDetailDtosBean> nurseProjectDetailDtos) {
        this.nurseProjectDetailDtos = nurseProjectDetailDtos;
    }

    public static class PatientHomePageWaitDealServiceDtoBean {
        /**
         * nonPayment : 11
         * waitingService : 0
         * onService : 0
         * finish : 0
         * mark : 0
         */

        @JSONField(name = "nonPayment")
        private int nonPayment;
        @JSONField(name = "waitingService")
        private int waitingService;
        @JSONField(name = "onService")
        private int onService;
        @JSONField(name = "finish")
        private int finish;
        @JSONField(name = "mark")
        private int mark;
        @JSONField(name = "unpayTag")
        private int unpayTag;

        public int getUnpayTag() {
            return unpayTag;
        }

        public void setUnpayTag(int unpayTag) {
            this.unpayTag = unpayTag;
        }

        public int getNonPayment() {
            return nonPayment;
        }

        public void setNonPayment(int nonPayment) {
            this.nonPayment = nonPayment;
        }

        public int getWaitingService() {
            return waitingService;
        }

        public void setWaitingService(int waitingService) {
            this.waitingService = waitingService;
        }

        public int getOnService() {
            return onService;
        }

        public void setOnService(int onService) {
            this.onService = onService;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }
    }

    public static class NurseCategoryIdAndNameDtosBean {
        /**
         * id : 1
         * name : 基础护理
         * code : 001
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "code")
        private String code;

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
    }

    public static class NurseProjectDetailDtosBean {
        /**
         * id : 1
         * name : 输液
         * icon : group1/M00/00/02/rBAEA1wQ_muAM7VKAAE6aVdTUtE235.png
         * summary : 专业护士静脉点滴
         * intro :
         * fee : 50
         */

        @JSONField(name = "id")
        private long id;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "icon")
        private String icon;
        @JSONField(name = "summary")
        private String summary;
        @JSONField(name = "intro")
        private String intro;
        @JSONField(name = "fee")
        private BigDecimal fee;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }
    }
}
