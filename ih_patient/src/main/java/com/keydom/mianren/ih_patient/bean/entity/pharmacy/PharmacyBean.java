package com.keydom.mianren.ih_patient.bean.entity.pharmacy;

import com.keydom.mianren.ih_patient.bean.DrugsDtos;

import java.io.Serializable;
import java.util.List;

public class PharmacyBean implements Serializable {


    private static final long serialVersionUID = 8445491864433997344L;
    private List<PrescriptionItemEntity> data;
    // private List<PharmacyEntity> data;
    private String subtotal;
    private String prescriptionType;
    private String drugstoreCode;
    private String prescriptionId;
    private Long distance;
    private String drugstoreAddress;
    private String drugstore;
    private String sumFee;
    private String qrcodeUrl;

    //配送费
    private double deliveryCost;

    private List<DrugsDtos> drugsDtos;


    public List<DrugsDtos> getDrugsDtos() {
        return drugsDtos;
    }

    public void setDrugsDtos(List<DrugsDtos> drugsDtos) {
        this.drugsDtos = drugsDtos;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDrugstoreAddress() {
        return drugstoreAddress;
    }

    public void setDrugstoreAddress(String drugstoreAddress) {
        this.drugstoreAddress = drugstoreAddress;
    }

    public String getDrugstore() {
        return drugstore;
    }

    public void setDrugstore(String drugstore) {
        this.drugstore = drugstore;
    }

    public String getSumFee() {
        return sumFee;
    }

    public void setSumFee(String sumFee) {
        this.sumFee = sumFee;
    }



    public String getDrugstoreCode() {
        return drugstoreCode;
    }

    public void setDrugstoreCode(String drugstoreCode) {
        this.drugstoreCode = drugstoreCode;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }


    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public List<PrescriptionItemEntity> getData() {
        return data;
    }

    public void setData(List<PrescriptionItemEntity> data) {
        this.data = data;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    @Override
    public String toString() {
        return "PharmacyBean{" +
                "data=" + data +
                ", subtotal='" + subtotal + '\'' +
                ", prescriptionType='" + prescriptionType + '\'' +
                ", drugstoreCode='" + drugstoreCode + '\'' +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", distance=" + distance +
                ", drugstoreAddress='" + drugstoreAddress + '\'' +
                ", drugstore='" + drugstore + '\'' +
                ", sumFee='" + sumFee + '\'' +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                '}';
    }
}
