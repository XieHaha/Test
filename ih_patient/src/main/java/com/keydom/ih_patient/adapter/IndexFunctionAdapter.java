package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.card_operate.CardoperateActivity;
import com.keydom.ih_patient.activity.function_config.FunctionConfigActivity;
import com.keydom.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.ih_patient.activity.order_doctor_register.OrderDoctorRegisterActivity;
import com.keydom.ih_patient.activity.order_examination.OrderExaminationActivity;
import com.keydom.ih_patient.activity.order_hospital_cure.OrderHospitalCureListActivity;
import com.keydom.ih_patient.activity.order_physical_examination.OrderPhysicalExaminationActivity;
import com.keydom.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
/**
 * 方法角标适配器
 */
public class IndexFunctionAdapter extends RecyclerView.Adapter<IndexFunctionAdapter.VH> {
    private Context context;
    private List<IndexFunction> indexFunctionList;
    //菜单配置
    public final String Setting="1";
    //预约挂号
    public final String DoctorRegister="22";
    //诊间缴费
    public final String  PaymentRecord="23";
    //办卡绑卡
    public final String Cardoperate="24";
    //在线问诊
    public final String OnlineDiagnose="25";
    //护理服务
    public final String NurseService="26";
    //报告查询
    public final String InspectionReport="27";
    //预约检查
    public final String OrderExamination="28";
    //预约住院
    public final String OrderHospitalCure="29";
    //预约体检
    public final String OrderPhysicalExamination="30";

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

        public VH(View v) {
            super(v);
            funcName = (TextView) v.findViewById(R.id.item_func_name);
            funcIcon = (ImageView) v.findViewById(R.id.item_func_icon);

        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_function_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH vh, final int position) {
        if (indexFunctionList.get(position) instanceof IndexFunction) {
            vh.funcIcon.setImageResource(indexFunctionList.get(position).getFunctionIcon());
            vh.funcName.setText(indexFunctionList.get(position).getName());
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Global.getUserId() == -1) {
                        ToastUtil.shortToast(context, context.getResources().getString(R.string.unlogin_hint));
                        return;
                    }
                    switch (String.valueOf(indexFunctionList.get(position).getId())) {
                        case Setting:
                            context.startActivity(new Intent(context, FunctionConfigActivity.class));
                            break;
                        case DoctorRegister:
                            OrderDoctorRegisterActivity.start(context);
                            break;
                        case PaymentRecord:
                            PaymentRecordActivity.start(context);
                            break;
                        case Cardoperate:
                            CardoperateActivity.start(context);
                            break;
                        case OnlineDiagnose:
                            EventBus.getDefault().post(new Event(EventType.SHOW_ONLINE_DIAGNOSE_PAGE, null));
                            break;
                        case NurseService:
                            EventBus.getDefault().post(new Event(EventType.SHOW_NURSE_SERVICE_PAGE, null));
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
