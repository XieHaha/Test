package com.keydom.mianren.ih_patient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.ElectronicCardActivity;
import com.keydom.mianren.ih_patient.activity.child_health.ChildHealthActivity;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.ManageUserActivity;
import com.keydom.mianren.ih_patient.activity.get_drug.GetDrugActivity;
import com.keydom.mianren.ih_patient.activity.health_manager.HealthManagerActivity;
import com.keydom.mianren.ih_patient.activity.hospital_payment.HospitalPaymentActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.logistic.QueryLogisticActivity;
import com.keydom.mianren.ih_patient.activity.medical_record.OutpatientRecordActivity;
import com.keydom.mianren.ih_patient.activity.nurse_main.NurseMainActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegistrationReserveActivity;
import com.keydom.mianren.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.OrderPhysicalExaminationActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisReserveActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.ReserveObstetricHospitalActivity;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.IndexFunction;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;
import com.keydom.mianren.ih_patient.constant.FunctionIndex;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * 方法角标适配器
 */
public class IndexFunctionAdapter extends RecyclerView.Adapter<IndexFunctionAdapter.VH> implements FunctionIndex {
    private Context context;
    private List<IndexFunction> indexFunctionList;


    private boolean loginStatus() {
        if (Global.getUserId() != -1) {
            return true;
        } else {
            ToastUtil.showMessage(context, R.string.unlogin_hint);
            return false;
        }
    }

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
            funcName = v.findViewById(R.id.item_func_name);
            funcIcon = v.findViewById(R.id.item_func_icon);
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
    public void onBindViewHolder(VH vh, @SuppressLint("RecyclerView") final int position) {
        if (indexFunctionList.get(position) != null) {
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
                    int index = (int) indexFunctionList.get(position).getId();
                    switch (index) {
                        case AmniocentesisReserve:
                            if (loginStatus()) {
                                AmniocentesisReserveActivity.start(context,
                                        AmniocentesisProtocol.AMNIOCENTESIS_WEB_RESERVE);
                            }
                            break;
                        case DoctorRegister:
                            if (loginStatus()) {
                                RegistrationReserveActivity.start(context);
                            }
                            break;
                        case PaymentRecord:
                            if (loginStatus()) {
                                PaymentRecordActivity.start(context);
                            }
                            break;
                        case Cardoperate:
                            if (loginStatus()) {
                                //                                CardoperateActivity.start
                                //                                (context);
                                ElectronicCardActivity.start(context);
                            }
                            break;
                        case OnlineDiagnose:
                            if (loginStatus()) {
                                DiagnoseMainActivity.start(context);
                            }
                            break;
                        case NurseService:
                            if (loginStatus()) {
                                NurseMainActivity.start(context);
                            }
                            break;
                        case InspectionReport:
                            if (loginStatus()) {
                                InspectionReportActivity.start(context, null, -1);
                            }
                            break;
                        case OrderExamination:
                            if (loginStatus()) {
                                OrderExaminationActivity.start(context);
                            }
                            break;
                        case OrderHospitalCure:
                            if (loginStatus()) {
                                ReserveObstetricHospitalActivity.start(context);
                            }
                            break;
                        case OrderPhysicalExamination:
                            OrderPhysicalExaminationActivity.start(context);
                            break;
                        case GetDrugs:
                            if (loginStatus()) {
                                context.startActivity(new Intent(context, GetDrugActivity.class));
                            }
                            break;
                        case ExpressInfo:
                            if (loginStatus()) {
                                context.startActivity(new Intent(context,
                                        QueryLogisticActivity.class));
                            }
                            break;
                        case OfflineEvaluation:
                            break;
                        case InquiryOrder:
                            if (loginStatus()) {
                                OnlineDiagnonsesOrderActivity.start(context,
                                        OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
                            }
                            break;
                        case UserManager:
                            if (loginStatus()) {
                                ManageUserActivity.start(context, ManageUserActivity.FROMUSERINDEX);
                            }
                            break;
                        case AddressManager:
                            if (loginStatus()) {
                                LocationManageActivity.start(context,
                                        Type.STARTLOCATIONWITHOUTRESULT);
                            }
                            break;
                        case PregnantWoman:
                            if (loginStatus()) {
                                // PregnantWomanActivity.start(context);
                                CommonDocumentActivity.start(context, "孕妇学校",
                                        Const.RELEASE_HOST + Const.WOMAN_SCHOOL + App.userInfo.getId());
                            }
                            break;
                        case PainlessDelivery:
                            if (loginStatus()) {
                                CommonDocumentActivity.start(context, CommonDocumentBean.CODE_19);
                            }
                            break;
                        case ObstetricMedical:
                            if (loginStatus()) {
                                //ObstetricMedicalActivity.start(context);
                                OutpatientRecordActivity.start(context);
                            }
                            break;
                        case HospitalPayment:
                            if (loginStatus()) {
                                HospitalPaymentActivity.start(context);
                            }
                            break;
                        case ChildHealth:
                            if (loginStatus()) {
                                ChildHealthActivity.start(context);
                            }
                            break;
                        case HealthManager:
//                            HealthManagerOpenActivity.start(context);
                            HealthManagerActivity.start(context);
                            break;
                     /*   case Setting:
                            context.startActivity(new Intent(context,
                                    FunctionConfigActivity.class));
                            break;
                        case Rehabilitation:
                            RehabilitationRecordActivity.start(context);
                            break;
                        case ObstetricHospital:
                            ReserveObstetricHospitalActivity.start
                                    (context);
                            break;
                        case ChildHealthCare:
                            ChildHealthActivity.start(context);
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
                            break;*/
                        default:
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
