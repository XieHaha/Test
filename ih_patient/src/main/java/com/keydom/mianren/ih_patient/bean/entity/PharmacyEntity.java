package com.keydom.mianren.ih_patient.bean.entity;

import java.io.Serializable;

public class PharmacyEntity implements Serializable {
    private String pharmacyName;
    private String pharmacyAddress;
    private String drugPrice;
    private String distance;
    private String imageUrl;
    boolean isSelected = false;


    private String prescriptionId;//处方id

    private String hospitalName;//处方来源（开方医院名称）
    private String hospitalAddress;

    private String name;//患者姓名
    private String age;//患者年龄
    private String sex;//患者性别

    private String address;//患者详情地址
    private String phoneNumber;//患者电话

    private String dept;//门诊科室
    private String casehistoryNumber;//门诊病历号
    private String clinical;//临床（初步诊断）
    private PharmacyPayEnum paystatus;//支付状态（0-未支付，1-已支付,2-已取药/已签收,3-已发货,4-拒收）
    private String acquireMedicine;//取药方式（0-到店自取，1-配送到家）
    private String acquireMedicineCode;//取药码
    private String drugstore;// 药店名称
    private String drugsStoreAddress;//药店地址


    private String totalFee;//支付金额
    private String valid;//是否有效（0-无效，1-有效）

    private String doctorId;// 开方医生id
    private String doctorName;//开方医生姓名
    private String auditerId;//审核人id

    private String auditerName;//审核人姓名

    private String checkerName;//核对、发药人姓名

    private String createDate;// 开方日期
    private String consigneeName;//收货人姓名

    private String consigneePhone;//收货人电话

    private String consigneeAddress;//收货人地址

    private String waybill;//运单号

    private String carrier;//运营商名称
    private String auditerDate;//审核日期

    private String checkerDate;// 发药日期

    private String signstatus;//签收/取药状态(0-未取药，1-已取药）

    private double drugsStoreLongitude;


    private double drugsStoreLatitude;


    public double getDrugsStoreLongitude() {
        return drugsStoreLongitude;
    }

    public void setDrugsStoreLongitude(double drugsStoreLongitude) {
        this.drugsStoreLongitude = drugsStoreLongitude;
    }

    public double getDrugsStoreLatitude() {
        return drugsStoreLatitude;
    }

    public void setDrugsStoreLatitude(double drugsStoreLatitude) {
        this.drugsStoreLatitude = drugsStoreLatitude;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCasehistoryNumber() {
        return casehistoryNumber;
    }

    public void setCasehistoryNumber(String casehistoryNumber) {
        this.casehistoryNumber = casehistoryNumber;
    }

    public String getClinical() {
        return clinical;
    }

    public void setClinical(String clinical) {
        this.clinical = clinical;
    }

    public PharmacyPayEnum getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(PharmacyPayEnum paystatus) {
        this.paystatus = paystatus;
    }

    public String getAcquireMedicine() {
        return acquireMedicine;
    }

    public void setAcquireMedicine(String acquireMedicine) {
        this.acquireMedicine = acquireMedicine;
    }

    public String getAcquireMedicineCode() {
        return acquireMedicineCode;
    }

    public void setAcquireMedicineCode(String acquireMedicineCode) {
        this.acquireMedicineCode = acquireMedicineCode;
    }

    public String getDrugstore() {
        return drugstore;
    }

    public void setDrugstore(String drugstore) {
        this.drugstore = drugstore;
    }

    public String getDrugsStoreAddress() {
        return drugsStoreAddress;
    }

    public void setDrugsStoreAddress(String drugsStoreAddress) {
        this.drugsStoreAddress = drugsStoreAddress;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAuditerId() {
        return auditerId;
    }

    public void setAuditerId(String auditerId) {
        this.auditerId = auditerId;
    }

    public String getAuditerName() {
        return auditerName;
    }

    public void setAuditerName(String auditerName) {
        this.auditerName = auditerName;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getAuditerDate() {
        return auditerDate;
    }

    public void setAuditerDate(String auditerDate) {
        this.auditerDate = auditerDate;
    }

    public String getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(String checkerDate) {
        this.checkerDate = checkerDate;
    }

    public String getSignstatus() {
        return signstatus;
    }

    public void setSignstatus(String signstatus) {
        this.signstatus = signstatus;
    }


    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(String drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    @Override
    public String toString() {
        return "PharmacyEntity{" +
                "pharmacyName='" + pharmacyName + '\'' +
                ", pharmacyAddress='" + pharmacyAddress + '\'' +
                ", drugPrice='" + drugPrice + '\'' +
                ", distance='" + distance + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isSelected=" + isSelected +
                ", prescriptionId='" + prescriptionId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dept='" + dept + '\'' +
                ", casehistoryNumber='" + casehistoryNumber + '\'' +
                ", clinical='" + clinical + '\'' +
                ", paystatus='" + paystatus + '\'' +
                ", acquireMedicine='" + acquireMedicine + '\'' +
                ", acquireMedicineCode='" + acquireMedicineCode + '\'' +
                ", drugstore='" + drugstore + '\'' +
                ", drugsStoreAddress='" + drugsStoreAddress + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", valid='" + valid + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", auditerId='" + auditerId + '\'' +
                ", auditerName='" + auditerName + '\'' +
                ", checkerName='" + checkerName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", consigneeAddress='" + consigneeAddress + '\'' +
                ", waybill='" + waybill + '\'' +
                ", carrier='" + carrier + '\'' +
                ", auditerDate='" + auditerDate + '\'' +
                ", checkerDate='" + checkerDate + '\'' +
                ", signstatus='" + signstatus + '\'' +
                '}';
    }
}
