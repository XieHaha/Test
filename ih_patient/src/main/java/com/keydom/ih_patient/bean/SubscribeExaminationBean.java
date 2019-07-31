package com.keydom.ih_patient.bean;

import java.io.Serializable;
import java.lang.String;

/**
 * created date: 2018/12/12 on 17:12
 * des:体检预约实体
 */
public class SubscribeExaminationBean implements Serializable{

    /**
     * id : 3
     * registerUserId : 19
     * orderId : 3
     * healthCheckCombId : 1
     * payState : 1
     * completeState : 1
     * cancelState : 0
     * deleteState : 0
     * icon : null
     * summary : 在健康筛查的基础上针对血脂、肝功能、肿瘤标记物等检查项目进行了细化，特别关注高血脂、心脑血管病、肝病等慢性病及常见肿瘤的筛查。包含心肺肝胆肾眼科筛查：全面心电图，胸片，肝胆肾超声检查，肝功能，眼科检查，筛查早期病变。心脑血管检查：血脂，胆红素三项，心电图，血糖，胸部正位片等。 从不同方面了解心脑血管功能及危险性预测。肿瘤筛查：对原发性肝炎、肺癌、胰脏癌、胃癌、结直肠癌、食道癌等！【已婚女性】：妇检、白带常规、宫颈刮片、子宫附件超声、乳腺彩超、筛查女性疾病如乳房、阴道炎、子宫、宫颈炎症及肿瘤等.
     * checkTime : null
     * checkTimeDesc : null
     * address : null
     * fee : 240.0
     * remark : null
     * orderNumber : 31234567
     * createTime : 2018-12-14 10:34:15
     * updateTime : 2018-12-13 15:54:40
     * payTime : null
     * name : 常规型（男性）
     * code : null
     * number : 1
     */

//    待付款：支付状态码 payState：0
//            取消状态码 cancelState：0

//    待体检：支付状态码 pay_state：1
//            完成状态码 completeState：0
//            取消状态码 cancelState：0

//    已完成：完成状态码 completeState：1

//    已取消：取消状态码 cancelState：1

    private long id;
    private long registerUserId;
    private long orderId;
    private long healthCheckCombId;
    private int payState;
    private int completeState;
    private int cancelState;
    private int deleteState;
    private String icon;
    private String summary;
    private String checkTime;
    private String checkTimeDesc;
    private String address;
    private String fee;
    private String remark;
    private String orderNumber;
    private String createTime;
    private String updateTime;
    private String payTime;
    private String name;
    private String code;
    private int number;
    private int itemState;

    private int refundState;

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }

    public int getItemState() {
        return itemState;
    }

    public void setItemState(int itemState) {
        this.itemState = itemState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(long registerUserId) {
        this.registerUserId = registerUserId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getHealthCheckCombId() {
        return healthCheckCombId;
    }

    public void setHealthCheckCombId(long healthCheckCombId) {
        this.healthCheckCombId = healthCheckCombId;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public int getCompleteState() {
        return completeState;
    }

    public void setCompleteState(int completeState) {
        this.completeState = completeState;
    }

    public int getCancelState() {
        return cancelState;
    }

    public void setCancelState(int cancelState) {
        this.cancelState = cancelState;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckTimeDesc() {
        return checkTimeDesc;
    }

    public void setCheckTimeDesc(String checkTimeDesc) {
        this.checkTimeDesc = checkTimeDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

