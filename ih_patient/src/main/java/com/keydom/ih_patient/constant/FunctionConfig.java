package com.keydom.ih_patient.constant;

import com.keydom.ih_patient.R;

/**
 * 首页功能配置类
 */
public class FunctionConfig {
    private static int ResourceId = R.mipmap.more_icon;
    //预约挂号
    public static final int DoctorRegister = 22;
    //诊间缴费
    public static final int PaymentRecord = 23;
    //办卡绑卡
    public static final int Cardoperate = 24;
    //在线问诊
    public static final int OnlineDiagnose = 25;
    //护理服务
    public static final int NurseService = 26;
    //报告查询
    public static final int InspectionReport = 27;
    //预约检查
    public static final int OrderExamination = 28;
    //预约住院
    public static final int OrderHospitalCure = 29;
    //预约体检
    public static final int OrderPhysicalExamination = 30;

    //取药用药
    public static final int GetDrugs = 31;

    //物流查询
    public static final int ExpressInfo = 32;

    public static int getIcon(long id) {
        if (id == DoctorRegister) {
            FunctionConfig.ResourceId = R.mipmap.consultation_orderby;

        } else if (id == PaymentRecord) {
            FunctionConfig.ResourceId = R.mipmap.consultation_pay;

        } else if (id == Cardoperate) {
            FunctionConfig.ResourceId = R.mipmap.create_code_icon;

        } else if (id == OnlineDiagnose) {
            FunctionConfig.ResourceId = R.mipmap.online_diagnose_icon;

        } else if (id == OrderExamination) {
            FunctionConfig.ResourceId = R.mipmap.appointment_register_icon;

        } else if (id == OrderPhysicalExamination) {
            FunctionConfig.ResourceId = R.mipmap.physical_check_icon;

        } else if (id == NurseService) {
            FunctionConfig.ResourceId = R.mipmap.mine_my_nurse;

        } else if (id == OrderHospitalCure) {
            FunctionConfig.ResourceId = R.mipmap.appointment_icon;

        } else if (id == InspectionReport) {
            FunctionConfig.ResourceId = R.mipmap.report_query;

        } else if (id == GetDrugs) {
            FunctionConfig.ResourceId = R.mipmap.icon_get_drugs;

        } else if (id == ExpressInfo) {
            FunctionConfig.ResourceId = R.mipmap.icon_express_info;

        } else {
            FunctionConfig.ResourceId = R.mipmap.consultation_pay;

        }
        return FunctionConfig.ResourceId;
    }
}
