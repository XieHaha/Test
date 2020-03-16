package com.keydom.mianren.ih_patient.bean.entity;

import com.keydom.mianren.ih_patient.bean.entity.pharmacy.infoEntity;

import java.io.Serializable;
import java.util.List;

public class LogisticsEntity implements Serializable {


    /**
     * 物流信息成功更新时间
     */
    private String updateTime;


    private int id;
    /**
     * 运单号
     */
    private String waybill;
    /**
     * 承运商
     */
    private String carrier;
    /**
     * 收件人姓名
     */
    private String consigneeName;
    /**
     * 收件人电话
     */

    private String consigneePhone;
    /**
     * 收件人地址
     */

    private String consigneeAddress;
    /**
     * 处方单号
     */

    private String prescriptionId;
    /**
     * 运单状态 运单状态  0-无轨迹 1-已揽收 2-在途中 3-签收 4-问题件
     */
    private LogisticsEnum status;
    /**
     * 物流信息
     */
    private String logisticsInformation;
    private String remark;
    private String imgAddress;
    private String orderTime;
    private String deliveryTime;
    private String drugstore;
    /**
     * 自己添加title
     */
    private String title;
    private List<infoEntity> infoList;

    public List<infoEntity> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<infoEntity> infoList) {
        this.infoList = infoList;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getLogisticsInformation() {
        return logisticsInformation;
    }

    public void setLogisticsInformation(String logisticsInformation) {
        this.logisticsInformation = logisticsInformation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDrugstore() {
        return drugstore;
    }

    public void setDrugstore(String drugstore) {
        this.drugstore = drugstore;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public LogisticsEnum getStatus() {
        return status;
    }

    public void setStatus(LogisticsEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogisticsEntity{" +
                "id=" + id +
                ", waybill='" + waybill + '\'' +
                ", carrier='" + carrier + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", status=" + status +
                ", logisticsInformation='" + logisticsInformation + '\'' +
                ", remark='" + remark + '\'' +
                ", imgAddress='" + imgAddress + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", drugstore='" + drugstore + '\'' +
                ", title='" + title + '\'' +
                ", infoList=" + infoList +
                '}';
    }
}
