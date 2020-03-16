package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.CardoperateActivity;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.mianren.ih_patient.activity.function_config.FunctionConfigActivity;
import com.keydom.mianren.ih_patient.activity.get_drug.GetDrugActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthManagerActivity;
import com.keydom.mianren.ih_patient.activity.hospital_payment.HospitalPaymentActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.ObstetricMedicalActivity;
import com.keydom.mianren.ih_patient.activity.logistic.QueryLogisticActivity;
import com.keydom.mianren.ih_patient.activity.medical_mail.MedicalMailActivity;
import com.keydom.mianren.ih_patient.activity.nurse_main.NurseMainActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegistrationReserveActivity;
import com.keydom.mianren.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.mianren.ih_patient.activity.order_hospital_cure.OrderHospitalCureListActivity;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.OrderPhysicalExaminationActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.mianren.ih_patient.activity.postpartum_rehabilitation.RehabilitationRecordActivity;
import com.keydom.mianren.ih_patient.activity.pregnant_woman.PregnantWomanActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisReserveActivity;
import com.keydom.mianren.ih_patient.activity.reserve_examination.ExaminationReserveActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.ReserveObstetricHospitalActivity;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.ReservePainlessDeliveryActivity;
import com.keydom.mianren.ih_patient.bean.IndexFunction;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * 方法角标适配器
 */
public class IndexFunctionAdapter extends RecyclerView.Adapter<IndexFunctionAdapter.VH> {
    private Context context;
    private List<IndexFunction> indexFunctionList;
    //菜单配置
    public final String Setting = "1";
    //产后康复
    public final String Rehabilitation = "15";
    //羊水穿刺预约
    public final String AmniocentesisApply = "16";
    //孕妇学校
    public final String PregnantWoman = "17";
    //产科门诊
    public final String ObstetricMedical = "18";
    //无痛分娩
    public final String PainlessDelivery = "19";
    //产科住院
    public final String ObstetricHospital = "20";
    //儿童保健
    public final String ChildHealthCare = "21";
    //预约挂号
    public final String DoctorRegister = "101";
    //诊间缴费
    public final String PaymentRecord = "102";
    //健康管理
    public final String HealthManager = "103";
    //病案邮寄
    public final String MedicalMail = "104";
    //检验检查预约
    public final String Examination = "105";
    //办卡绑卡
    public final String CardOperate = "107";

    //非会员
    //预约挂号
    public final String DoctorRegisterNonMember = "22";
    //诊间缴费
    public final String PaymentRecordNonMember = "23";
    //办卡绑卡
    public final String CardOperateNonMember = "24";
    //在线问诊
    public final String OnlineDiagnose = "25";
    //护理服务
    public final String NurseService = "26";
    //报告查询
    public final String InspectionReport = "27";
    //预约检查
    public final String OrderExamination = "28";
    //预约住院
    public final String OrderHospitalCure = "29";
    //预约体检
    public final String OrderPhysicalExamination = "30";
    //取药用药
    public final String GetDrugs = "31";
    //物流查询
    public final String ExpressInfo = "32";
    //线下评估预约
    public final String OfflineEvaluation = "33";
    //住院预缴金
    public final String HospitalPayment = "34";

    /**
     * 构造方法
     */
    public IndexFunctionAdapter(Context context, List<IndexFunction> indexFunctionList) {
        this.context = context;
        this.indexFunctionList = indexFunctionList;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView funcName;
        public ImageView funcIcon;
        public View redPointView;

        public VH(View v) {
            super(v);
            funcName = (TextView) v.findViewById(R.id.item_func_name);
            funcIcon = (ImageView) v.findViewById(R.id.item_func_icon);
            redPointView = v.findViewById(R.id.item_redpoint_view);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_function_item,
                parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH vh, final int position) {
        if (indexFunctionList.get(position) instanceof IndexFunction) {
            vh.funcIcon.setImageResource(indexFunctionList.get(position).getFunctionIcon());

            vh.funcName.setText(indexFunctionList.get(position).getName());
            if (indexFunctionList.get(position).isRedPointShow()) {
                vh.redPointView.setVisibility(View.VISIBLE);
            } else {
                vh.redPointView.setVisibility(View.GONE);

            }

            vh.funcName.setText(indexFunctionList.get(position).getName());

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  if (Global.getUserId() == -1) {
                        ToastUtil.showMessage(context, context.getResources().getString(R.string
                        .unlogin_hint));
                        return;
                    }*/
                    switch (String.valueOf(indexFunctionList.get(position).getId())) {
                        case Setting:
                            context.startActivity(new Intent(context,
                                    FunctionConfigActivity.class));
                            break;
                        case Rehabilitation:
                            RehabilitationRecordActivity.start(context);
                            break;
                        case AmniocentesisApply:
                            AmniocentesisReserveActivity.start(context);
                            break;
                        case PregnantWoman:
                            PregnantWomanActivity.start(context);
                            break;
                        case ObstetricMedical:
                            ObstetricMedicalActivity.start(context);
                            break;
                        case PainlessDelivery:
                            ReservePainlessDeliveryActivity.start(context);
                            break;
                        case ObstetricHospital:
                            ReserveObstetricHospitalActivity.start(context);
                            break;
                        case ChildHealthCare:
                            ChildHealthActivity.start(context);
                            break;
                        case DoctorRegister:
                        case DoctorRegisterNonMember:
                            RegistrationReserveActivity.start(context);
                            break;
                        case PaymentRecord:
                        case PaymentRecordNonMember:
                            PaymentRecordActivity.start(context);
                            break;
                        case HealthManager:
                            HealthManagerActivity.start(context);
                            break;
                        case MedicalMail:
                            MedicalMailActivity.start(context);
                            break;
                        case Examination:
                            ExaminationReserveActivity.start(context);
                            break;
                        case CardOperate:
                        case CardOperateNonMember:
                            CardoperateActivity.start(context);
                            break;
                        case OnlineDiagnose:
                            DiagnoseMainActivity.start(context);
                            break;
                        case NurseService:
                            NurseMainActivity.start(context);
                            break;
                        case InspectionReport:
                            InspectionReportActivity.start(context);
                            break;
                        case OrderExamination:
                            OrderExaminationActivity.start(context);
                            break;
                        case OrderHospitalCure:
                            OrderHospitalCureListActivity.start(context);
                            break;
                        case OrderPhysicalExamination:
                            OrderPhysicalExaminationActivity.start(context);
                            break;
                        case GetDrugs:
                            context.startActivity(new Intent(context, GetDrugActivity.class));
                            break;
                        case ExpressInfo:
                            context.startActivity(new Intent(context, QueryLogisticActivity.class));
                            break;
                        case OfflineEvaluation:
                            break;
                        case HospitalPayment:
                            HospitalPaymentActivity.start(context);
                            break;
                    }
                }
            });
        } else {
            Logger.e("类型异常");
        }

    }

    @Override
    public int getItemCount() {
        return indexFunctionList.size();
    }
}