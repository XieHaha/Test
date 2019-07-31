package com.keydom.ih_doctor.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.bean
 * @Description：普通护理订单详情对象
 * @Author：song
 * @Date：18/12/21 下午3:20
 * 修改人：xusong
 * 修改时间：18/12/21 下午3:20
 */
public class CommonNurseServiceOrderDetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean needAccept;
    private boolean transfered;
    private List<NurseServiceRecoderBean> nurseServiceRecordDetailDtos;
    private HeadNurseServiceOrderDetailBean nursingServiceOrderDetailBaseDto;
    private List<NursingPatientEquipmentItem> nursingPatientEquipmentItemDtos;
    private List<NurseSubOrderBean> subOrderDetails;

    public boolean isNeedAccept() {
        return needAccept;
    }

    public void setNeedAccept(boolean needAccept) {
        this.needAccept = needAccept;
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void setTransfered(boolean transfered) {
        this.transfered = transfered;
    }

    public List<NurseServiceRecoderBean> getNurseServiceRecordDetailDtos() {
        return nurseServiceRecordDetailDtos;
    }

    public void setNurseServiceRecordDetailDtos(List<NurseServiceRecoderBean> nurseServiceRecordDetailDtos) {
        this.nurseServiceRecordDetailDtos = nurseServiceRecordDetailDtos;
    }

    public HeadNurseServiceOrderDetailBean getNursingServiceOrderDetailBaseDto() {
        return nursingServiceOrderDetailBaseDto;
    }

    public void setNursingServiceOrderDetailBaseDto(HeadNurseServiceOrderDetailBean nursingServiceOrderDetailBaseDto) {
        this.nursingServiceOrderDetailBaseDto = nursingServiceOrderDetailBaseDto;
    }

    public List<NursingPatientEquipmentItem> getNursingPatientEquipmentItemDtos() {
        return nursingPatientEquipmentItemDtos;
    }

    public void setNursingPatientEquipmentItemDtos(List<NursingPatientEquipmentItem> nursingPatientEquipmentItemDtos) {
        this.nursingPatientEquipmentItemDtos = nursingPatientEquipmentItemDtos;
    }

    public List<NurseSubOrderBean> getSubOrderDetails() {
        return subOrderDetails;
    }

    public void setSubOrderDetails(List<NurseSubOrderBean> subOrderDetails) {
        this.subOrderDetails = subOrderDetails;
    }
}
