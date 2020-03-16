package com.keydom.mianren.ih_patient.bean.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GetDrugEntity implements Serializable {


    private String acquireMedicineCode;//二维码

    private String id;
    /**
     * 医生ID
     */
    private String doctorId;
    /**
     * 处方来源（开方医院名称）
     */
    private String hospitalName;
    /**
     * 处方订单ID
     */
    private String prescriptionId;//处方单号
    /**
     * 医院地址
     */
    private String hospitalAddress;
    /**
     * 药品供应商
     */
    private String drugsSupplier;
    /**
     * 门诊科室
     */
    private String dept;
    /**
     * 患者性别（0-男 1-女 2-其他）
     */
    private int sex;
    /**
     * 药店
     */
    private String drugstore;
    /**
     * 药店代码
     */
    private String drugstoreCode;
    /**
     * 药店地址
     */
    private String drugsStoreAddress;
    /**
     * 药店
     */
    private String drugsStoreLongitude;
    /**
     * 药店
     */
    private String drugsStoreLatitude;

    /**
     * 医疗机构代码(医院名称)
     */
    private String hospitalCode;
    /**
     * 总费用
     */
    private BigDecimal totalFee;
    /**
     * 患者名字
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 是否有效（0-无效，1-有效）
     */
    private int valid;
    /**
     * 审核人姓名
     */
    private String auditerName;
    /**
     * 处方明细的所有数据
     */
    private String prescriptionItem;
    /**
     * 门诊病历号
     */
    private String casehistoryNumber;
    /**
     * 审核日期
     */
    private String auditerDate;
    /**
     * 开方日期
     */
    private String createDate;
    /**
     * 开方医生姓名
     */
    private String doctorName;
    /**
     * 取药方式（0-到店自取，1-配送到家）
     */
    private String acquireMedicine;//0-到店自取，1-配送到家
    /**
     * 核对、发药人姓名
     */
    private String checkerName;
    private String age;
    /**
     * 下单时间
     */
    private String orderTime;
    /**
     * 审核人ID
     */
    private String auditerId;
    /**
     * 发药日期
     */
    private String checkerDate;
    /**
     * 临床（初步诊断）
     */
    private String clinical;
    /**
     * 收件人姓名
     */
    private String consigneeName;
    /**
     * 收件人电话
     */
    private String consigneePhone;
    /**
     * 支付状态（0-未支付，1-已支付,2-已取药/已签收,3-已发货,4-拒收）
     */
    private PharmacyPayEnum paystatus;
    /**
     * 患者端 -患者id
     */
    private String userId;
    /**
     * 收件人地址
     */
    private String consigneeAddress;

    public PharmacyPayEnum getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(PharmacyPayEnum paystatus) {
        this.paystatus = paystatus;
    }

    public String getAcquireMedicineCode() {
        return acquireMedicineCode;
    }

    public void setAcquireMedicineCode(String acquireMedicineCode) {
        this.acquireMedicineCode = acquireMedicineCode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getDrugsSupplier() {
        return drugsSupplier;
    }

    public void setDrugsSupplier(String drugsSupplier) {
        this.drugsSupplier = drugsSupplier;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDrugstore() {
        return drugstore;
    }

    public void setDrugstore(String drugstore) {
        this.drugstore = drugstore;
    }

    public String getDrugstoreCode() {
        return drugstoreCode;
    }

    public void setDrugstoreCode(String drugstoreCode) {
        this.drugstoreCode = drugstoreCode;
    }

    public String getDrugsStoreAddress() {
        return drugsStoreAddress;
    }

    public void setDrugsStoreAddress(String drugsStoreAddress) {
        this.drugsStoreAddress = drugsStoreAddress;
    }

    public String getDrugsStoreLongitude() {
        return drugsStoreLongitude;
    }

    public void setDrugsStoreLongitude(String drugsStoreLongitude) {
        this.drugsStoreLongitude = drugsStoreLongitude;
    }

    public String getDrugsStoreLatitude() {
        return drugsStoreLatitude;
    }

    public void setDrugsStoreLatitude(String drugsStoreLatitude) {
        this.drugsStoreLatitude = drugsStoreLatitude;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getAuditerName() {
        return auditerName;
    }

    public void setAuditerName(String auditerName) {
        this.auditerName = auditerName;
    }

    public String getPrescriptionItem() {
        return prescriptionItem;
    }

    public void setPrescriptionItem(String prescriptionItem) {
        this.prescriptionItem = prescriptionItem;
    }

    public String getCasehistoryNumber() {
        return casehistoryNumber;
    }

    public void setCasehistoryNumber(String casehistoryNumber) {
        this.casehistoryNumber = casehistoryNumber;
    }

    public String getAuditerDate() {
        return auditerDate;
    }

    public void setAuditerDate(String auditerDate) {
        this.auditerDate = auditerDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAcquireMedicine() {
        return acquireMedicine;
    }

    public void setAcquireMedicine(String acquireMedicine) {
        this.acquireMedicine = acquireMedicine;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAuditerId() {
        return auditerId;
    }

    public void setAuditerId(String auditerId) {
        this.auditerId = auditerId;
    }

    public String getCheckerDate() {
        return checkerDate;
    }

    public void setCheckerDate(String checkerDate) {
        this.checkerDate = checkerDate;
    }

    public String getClinical() {
        return clinical;
    }

    public void setClinical(String clinical) {
        this.clinical = clinical;
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



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }
}
