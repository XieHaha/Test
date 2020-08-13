package com.keydom.mianren.ih_patient.constant;

import com.keydom.mianren.ih_patient.R;

/**
 * 首页功能配置类
 */
public class FunctionConfig implements FunctionIndex {
    private static int ResourceId = R.mipmap.more_icon;

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
        } else if (id == OfflineEvaluation) {
            FunctionConfig.ResourceId = R.mipmap.icon_express_info;
        } else if (id == AmniocentesisReserve) {
            FunctionConfig.ResourceId = R.mipmap.vip_amniocentesis_icon;
        } else if (id == HospitalPayment) {
            FunctionConfig.ResourceId = R.mipmap.icon_express_info;
        } else if (id == InquiryOrder) {
            FunctionConfig.ResourceId = R.mipmap.mine_physician;
        } else if (id == UserManager) {
            FunctionConfig.ResourceId = R.mipmap.mine_people;
        } else if (id == AddressManager) {
            FunctionConfig.ResourceId = R.mipmap.mine_location;
        } else {
            FunctionConfig.ResourceId = R.mipmap.consultation_pay;
        }
        return FunctionConfig.ResourceId;
    }
}
