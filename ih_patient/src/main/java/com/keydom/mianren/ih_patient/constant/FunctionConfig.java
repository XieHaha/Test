package com.keydom.mianren.ih_patient.constant;

import com.keydom.mianren.ih_patient.R;

/**
 * 首页功能配置类
 *
 * @author 顿顿
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
            FunctionConfig.ResourceId = R.mipmap.vip_obstetric_order_icon;
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
            FunctionConfig.ResourceId = R.mipmap.icon_hospital_payment;
        } else if (id == InquiryOrder) {
            FunctionConfig.ResourceId = R.mipmap.mine_physician;
        } else if (id == UserManager) {
            FunctionConfig.ResourceId = R.mipmap.mine_people;
        } else if (id == AddressManager) {
            FunctionConfig.ResourceId = R.mipmap.mine_location;
        } else if (id == PregnantWoman) {
            FunctionConfig.ResourceId = R.mipmap.vip_pregnancy_school_icon;
        } else if (id == PainlessDelivery) {
            FunctionConfig.ResourceId = R.mipmap.vip_painless_labor_icon;
        } else if (id == ObstetricMedical) {
            FunctionConfig.ResourceId = R.mipmap.vip_obstetrical_medical_records_icon;
        } else if (id == ChildHealth) {
            FunctionConfig.ResourceId = R.mipmap.vip_child_health_icon;
        } else if (id == HealthManager) {
            FunctionConfig.ResourceId = R.mipmap.icon_health_manager;
        } else {
            FunctionConfig.ResourceId = R.mipmap.consultation_pay;
        }
        return FunctionConfig.ResourceId;
    }
}
