package com.keydom.ih_patient.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.keydom.ih_patient.App;

import java.io.Serializable;

/**
 * 消息实体
 */
public class MessageBean implements Serializable {
    private static final long serialVersionUID = 4211283663109147389L;

    /**
     * id : 1018
     * infoType : APPOINT_CHECK
     * orderNum : 上午0号
     * userName : 刘杰
     * userId : 19
     * userPhone : 13982237770
     * hospitalName : 人民医院
     * hospitalCat :
     * doctorsName :
     * doctorsPhone :
     * visitLocation : 科华北路20号
     * visitProject : 2级项目
     * visitProjectNum :
     * visitApplyTime :
     * visitTime : 2019-02-22 08:00-09:00
     * visitNum :
     * pay :
     * remarks :
     * dateTime : 2019-02-22 17:46:45
     * isRead : 1
     */

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "infoType")
    private String infoType;
    @JSONField(name = "orderNum")
    private String orderNum;
    @JSONField(name = "userName")
    private String userName;
    @JSONField(name = "userId")
    private String userId;
    @JSONField(name = "userPhone")
    private String userPhone;
    @JSONField(name = "hospitalName")
    private String hospitalName;
    @JSONField(name = "hospitalCat")
    private String hospitalCat;
    @JSONField(name = "doctorsName")
    private String doctorsName;
    @JSONField(name = "doctorsPhone")
    private String doctorsPhone;
    @JSONField(name = "visitLocation")
    private String visitLocation;
    @JSONField(name = "visitProject")
    private String visitProject;
    @JSONField(name = "visitProjectNum")
    private String visitProjectNum;
    @JSONField(name = "visitApplyTime")
    private String visitApplyTime;
    @JSONField(name = "visitTime")
    private String visitTime;
    @JSONField(name = "visitNum")
    private String visitNum;
    @JSONField(name = "pay")
    private String pay;
    @JSONField(name = "remarks")
    private String remarks;
    @JSONField(name = "dateTime")
    private String dateTime;
    @JSONField(name = "isRead")
    private String isRead;
    @JSONField(name = "extends1")
    private String extends1;
    private String title;

    @JSONField(name = "type")
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtends1() {
        return extends1;
    }

    public void setExtends1(String extends1) {
        if (extends1 != null)
            this.extends1 = extends1;
        else
            this.extends1 = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null)
            this.title = title;
        else
            this.title = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        if (infoType != null)
            this.infoType = infoType;
        else
            this.infoType = "";
        switch (infoType) {
            case "APPOINT_REGISTR":
                setTitle("预约挂号成功通知");
                break;
            case "VISIT_REMIND":
                setTitle("就诊日提醒");
                break;
            case "CANCEL_APPOINT_REGISTR":
                setTitle("预约挂号取消通知");
                break;
            case "APPOINT_CHECK":
                setTitle("预约检查成功通知");
                break;
            case "ADJUST_APPOINT_CHECK":
                setTitle("预约检查调整通知");
                break;
                case "APPOINT_HOSPITAL":
                setTitle("住院预约成功通知");
                break;
            case "APPOINT_HOSPITAL_CHECKIN":
                setTitle("住院报到通知");
                break;
            case "APPOINT_INQUIRY":
                setTitle("在线问诊申请成功通知");
                break;
            case "CANCEL_APPOINT_INQUIRY":
                setTitle("在线问诊取消通知");
                break;
            case "REFER_APPOINT_INQUIRY":
                setTitle("在线问诊换诊通知");
                break;
            case "CONVER_APPOINT_INQUIRY":
                setTitle("在线问诊转诊通知");
                break;
            case "NOTIC_APPOINT_INQUIRY":
                setTitle("在线问诊开始提醒");
                break;
            case "CHECK_APPOINT_INQUIRY":
                setTitle("在线问诊检验通知");
                break;
            case "INSPECT_APPOINT_INQUIRY":
                setTitle("在线问诊检查通知");
                break;
            case "APPOINT_NURSING":
                setTitle("护理服务预约成功通知");
                break;
            case "CANCEL_APPOINT_NURSING":
                setTitle("护理服务取消通知");
                break;
            case "CHARG_APPOINT_NURSING":
                setTitle("护理服务退单通知");
                break;
            case "CHARGBACK_APPOINT_NURSING":
                setTitle("护理服务退回修改通知");
                break;
            case "ACCEPTUSER_APPOINT_NURSING":
                setTitle("护理服务接单通知");
                break;
            case "ACCEPT_APPOINT_NURSING":
                setTitle("护理服务接收通知");
                break;
            case "ACCEPTCHANGE_APPOINT_NURSING":
                setTitle("护理服务转单通知");
                break;
            case "ADD_APPOINT_NURSING":
                setTitle("护理服务新增服务项目通知");
                break;
            case "EISI_APPOINT_NURSING":
                setTitle("护理服务器材/耗材支付通知");
                break;
            case "PRESCRIPTION_APPOINT_INQUIRY":
                setTitle("在线问诊处方通知");
                break;
            case "APPOINT_CHECK_NOTICE":
                setTitle("检查预约提醒");
            case "CLINIC_PAY":
                setTitle("诊间缴费通知");
                break;
        }
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        if (orderNum != null)
            this.orderNum = orderNum;
        else
            this.orderNum = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName != null)
            this.userName = userName;
        else
            this.userName = "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId != null)
            this.userId = userId;
        else
            this.userId = "";
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        if (userPhone != null)
            this.userPhone = userPhone;
        else
            this.userPhone = "";
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        if (hospitalName != null)
            this.hospitalName = hospitalName;
        else
            this.hospitalName = "";
    }

    public String getHospitalCat() {
        return hospitalCat;
    }

    public void setHospitalCat(String hospitalCat) {
        if (hospitalCat != null)
            this.hospitalCat = hospitalCat;
        else
            this.hospitalCat = "";
    }

    public String getDoctorsName() {
        return doctorsName;
    }

    public void setDoctorsName(String doctorsName) {
        if (doctorsName != null)
            this.doctorsName = doctorsName;
        else
            this.doctorsName = "";
    }

    public String getDoctorsPhone() {
        return doctorsPhone;
    }

    public void setDoctorsPhone(String doctorsPhone) {
        if (doctorsPhone != null)
            this.doctorsPhone = doctorsPhone;
        else
            this.doctorsPhone = "";
    }

    public String getVisitLocation() {
        return visitLocation;
    }

    public void setVisitLocation(String visitLocation) {
        if (visitLocation != null)
            this.visitLocation = visitLocation;
        else
            this.visitLocation = "";
    }

    public String getVisitProject() {
        return visitProject;
    }

    public void setVisitProject(String visitProject) {
        if (visitProject != null)
            this.visitProject = visitProject;
        else
            this.visitProject = "";
    }

    public String getVisitProjectNum() {
        return visitProjectNum;
    }

    public void setVisitProjectNum(String visitProjectNum) {
        if (visitProjectNum != null)
            this.visitProjectNum = visitProjectNum;
        else
            this.visitProjectNum = "";
    }

    public String getVisitApplyTime() {
        return visitApplyTime;
    }

    public void setVisitApplyTime(String visitApplyTime) {
        if (visitApplyTime != null)
            this.visitApplyTime = visitApplyTime;
        else
            this.visitApplyTime = "";
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        if (visitTime != null)
            this.visitTime = visitTime;
        else
            this.visitTime = "";
    }

    public String getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(String visitNum) {
        if (visitNum != null)
            this.visitNum = visitNum;
        else
            this.visitNum = "";
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        if (pay != null)
            this.pay = pay;
        else
            this.pay = "";
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        if (remarks != null)
            this.remarks = remarks;
        else
            this.remarks = "";
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        if (dateTime != null)
            this.dateTime = dateTime;
        else
            this.dateTime = "";
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        if (isRead != null)
            this.isRead = isRead;
        else
            this.isRead = "";
    }

    public String toString() {
        String result = "";
        switch (infoType) {
            case "APPOINT_REGISTR":
                result = "就诊人: " + userName + "<br>"
                        + "科室: " + hospitalCat + "<br>"
                        + "医生: " + doctorsName + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "就诊时间: " + visitTime + "<br>"
                        + "就诊地点: " + visitLocation + "<br>"
                        + "就诊序号: " + visitNum + "<br>"
                        + "备注: " + remarks + "<br>"
                        + "<font color='#F83535'>如需退号，请提前一天退号，及时看诊，过期作废。</font>";

                break;
            case "VISIT_REMIND":
                result ="你预约的明日就诊，请准时前往医院"+"<br>"
                        +"就诊人: " + userName + "<br>"
                        + "预约时间: " + visitTime + "<br>"
                        + "预约医院: " + hospitalName + "<br>"
                        + "预约科室: " + hospitalCat + "<br>"
                        + "预约医生: " + doctorsName + "<br>"
                        + "<font color='#F83535'>如需退号，请于今日23:00前在“挂号订单”中取消预约。</font>";
                break;
            case "CANCEL_APPOINT_REGISTR":
                result = "就诊人: " + userName + "<br>"
                        + "科室: " + hospitalCat + "<br>"
                        + "医生: " + doctorsName + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "就诊时间: " + visitTime + "<br>"
                        + "取消时间: " + dateTime + "<br>"
                        + "<font color='#F83535'>挂号费将在5个工作日内按支付路径退回到您的付款账号中。</font>";
                break;
            case "APPOINT_CHECK_NOTICE":
                result = "就诊人: " + userName + "<br>"
                        + "检查项目: " + visitProject + "<br>"
                        + "检查时段: " + visitTime + "<br>"
                        + "号序: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "检查地点: " + visitLocation + "<br>"
                        + "<font color='#F83535'>请及时检查，过期作废。</font>";
                break;
            case "ADJUST_APPOINT_CHECK":
                result = "就诊人: " + userName + "<br>"
                        + "检查项目: " + visitProject + "<br>"
                        + "原检查时段: " + visitApplyTime + "<br>"
                        + "现检查时段: " + visitTime + "<br>"
                        + "原号序: " + visitProjectNum + "<br>"
                        + "现号序: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "检查地点: " + visitLocation + "<br>"
                        + "<font color='#F83535'>请及时检查，过期作废。</font>";
                break;
            case "APPOINT_HOSPITAL":
                result = "就诊人: " + userName + "<br>"
                        + "入院证日期: " + dateTime + "<br>"
                        + "建议入院科室: " + hospitalCat + "<br>"
                        + "申请日期: " + dateTime + "<br>"
                        + "预计候床时间: " + visitTime + "<br>"
                        + "<font color='#F83535'>请认真阅读《入院注意事项》，医院确定报到日期后，会及时通知您，请耐心等待。</font>";
                break;
            case "APPOINT_HOSPITAL_CHECKIN":
                result = "就诊人: " + userName + "<br>"
                        + "建议入院科室: " + hospitalCat + "<br>"
                        + "报到日期: " + visitTime + "<br>"
                        + "报到地点: " + visitLocation + "<br>"
                        + "<font color='#F83535'>报到前请认真阅读《入院注意事项》。</font>";
                break;
            case "APPOINT_INQUIRY":
                if("视频".equals(visitProject))
                result = "订单号:"+orderNum+"<br>"
                        +"就诊人: " + userName + "<br>"
                        + "医生: " + doctorsName + "<br>"
                        + "科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "问诊类型: " + visitProject + "问诊"+"<br>"
                        + "问诊费用: " + pay + "<br>"
                        + "申请时间: " + dateTime + "<br>"
                        + "申请人: " + App.userInfo.getUserName() + "<br>"
                        + "<font color='#F83535'>请保持手机畅通，医生会尽快与您进行视频问诊。</font>";
                else
                    result = "订单号:"+orderNum+"<br>"
                            +"就诊人: " + userName + "<br>"
                            + "医生: " + doctorsName + "<br>"
                            + "科室: " + hospitalCat + "<br>"
                            + "医院: " + hospitalName + "<br>"
                            + "问诊类型: " + visitProject + "问诊"+ "<br>"
                            + "问诊费用: " + pay + "<br>"
                            + "申请时间: " + dateTime + "<br>"
                            + "申请人: " + App.userInfo.getUserName() + "<br>"
                            + "<font color='#F83535'>请保持手机畅通，医生会尽快与您进行图文问诊。</font>";
                break;
            case "CANCEL_APPOINT_INQUIRY":
                result = "就诊人: " + userName + "<br>"
                        + "医生: " + doctorsName + "<br>"
                        + "科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "问诊类型: " + visitProject + "<br>"
                        + "问诊费用: " + pay + "<br>"
                        + "取消时间: " + dateTime + "<br>"
                        + "<font color='#F83535'>问诊费用将在5个工作日内按支付路径退回到您的付款账号中。</font>";
                break;
            case "REFER_APPOINT_INQUIRY":
                result ="订单号: " + orderNum + "<br>"
                        +"就诊人: " + userName + "<br>"
                        + "原医生: " + extends1 + "<br>"
                        + "现医生: " + doctorsName + "<br>"
                        + "科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "问诊类型: " + visitProject + "<br>"
                        + "问诊费用: " + pay + "<br>"
                        + "<font color='#F83535'>请保持手机畅通，医生会尽快与您进行视频问诊。</font>";
                break;
            case "CONVER_APPOINT_INQUIRY":
                result = "订单号: " + orderNum + "<br>"
                        +"就诊人: " + userName + "<br>"
                        + "转诊医生: " + extends1 + "<br>"
                        + "接收医生: " + doctorsName + "<br>"
                        + "接收科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "问诊类型: " + visitProject + "问诊"+"<br>"
                        + "问诊费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“问诊订单”，再进入对应问诊订单进行转诊确认，并及时支付问诊费用。</font>";
                break;
            case "NOTIC_APPOINT_INQUIRY":
//                setTitle("");
                break;
            case "CHECK_APPOINT_INQUIRY":
                result ="订单号: " + orderNum + "<br>"
                        +"就诊人: " + userName + "<br>"
                        + "开单医生: " + doctorsName + "<br>"
                        + "开单科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "检验项目: " + visitProject + "<br>"
                        + "检验费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“诊间缴费”选择相应的检验项目进行支付，支付成功后及时到医院检验。</font>";
                break;
            case "INSPECT_APPOINT_INQUIRY":
                result ="订单号: " + orderNum + "<br>"
                        +"就诊人: " + userName + "<br>"
                        + "开单医生: " + doctorsName + "<br>"
                        + "开单科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "检查项目: " + visitProject + "<br>"
                        + "检查费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“诊间缴费”选择相应的检验项目进行支付，支付成功后及时到医院检查。</font>";
                break;

            case "PRESCRIPTION_APPOINT_INQUIRY":
                result ="订单号: " + orderNum + "<br>"
                        +"就诊人: " + userName + "<br>"
                        + "开单医生: " + doctorsName + "<br>"
                        + "开单科室: " + hospitalCat + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "处方费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“诊间缴费”选择相应的处方单进行支付。</font>";
                break;
            case "APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "提交人: " + App.userInfo.getUserName() + "<br>"
                        + "<font color='#F83535'>医院将尽快为您安排上门护士，如需取消订单，请打开APP，进入“护理服务订单”，选择对应订单进行取消。</font>";
                break;
            case "CANCEL_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "取消时间: " +dateTime+ "<br>"
                        + "<font color='#F83535'>服务费用将在5个工作日内按支付路径退回到您的付款账号中。</font>";
                break;
            case "CHARG_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "系统退单时间: " + dateTime + "<br>"
                        + "<font color='#F83535'>服务费用将在5个工作日内按支付路径退回到您的付款账号中。</font>";
                break;
            case "CHARGBACK_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "退回修改意见: " + remarks + "<br>"
                        + "<font color='#F83535'>请及时修改后再次提交订单。</font>";
                break;
            case "ACCEPTUSER_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "接单护士: " + remarks + "<br>"
                        + "上门护士: " + doctorsName + "<br>"
                        + "<font color='#F83535'>请注意接听上门护士电话，确认上门服务时间和相关事项。</font>";
                break;
            case "ACCEPT_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " +  userName+ "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "上门护士: " + doctorsName + "<br>"
                        + "<font color='#F83535'>上门护士"+doctorsName+"已接收订单，并将于约定时间上门服务，请注意做好相应准备。</font>";
                break;
            case "ACCEPTCHANGE_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "预约上门时间: " + visitTime + "<br>"
                        + "服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>"
                        + "转单护士: " + remarks + "<br>"
                        + "接收护士: " +  doctorsName+ "<br>"
                        + "<font color='#F83535'>上门护士"+remarks+"转单给"+doctorsName+"护士为您提供上门服务。</font>";
                break;
            case "ADD_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        +"服务对象: " + userName + "<br>"
                        + "服务时间: " + visitTime + "<br>"
                        + "本次新增服务项目: " + visitProject + "<br>"
                        + "服务次数: " + visitProjectNum + "<br>"
                        + "服务费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“诊间缴费”，选择对应缴费项目进行支付。</font>";
                break;
            case "EISI_APPOINT_NURSING":
                result ="订单号: " + orderNum + "<br>"
                        + "服务时间: " + visitTime + "<br>"
                        + "本次服务项目: " + visitProject + "<br>"
                        + "本次器材/耗材费用: " + pay + "<br>"
                        + "<font color='#F83535'>请进入“诊间缴费”，选择对应缴费项目进行支付。</font>";
                break;
            case  "APPOINT_CHECK":
                result="订单号: " + orderNum + "<br>"
                        + "检查时间: " + dateTime + "<br>"
                        +"就诊人对象: " + userName + "<br>"
                        + "检查项目: " + visitProject + "<br>"
                        + "检查时段: " + visitTime + "<br>"
                        + "号序: " + visitNum + "<br>"
                        + "医院: " + hospitalName + "<br>";
        }
        return result;
    }
}
