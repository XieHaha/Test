package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.DianoseCaseDetailActivity;
import com.keydom.ih_doctor.activity.online_diagnose.ReportListActivity;
import com.keydom.ih_doctor.activity.prescription_check.PrescriptionActivity;
import com.keydom.ih_doctor.bean.DiagnoseRecoderItemBean;
import com.keydom.ih_doctor.m_interface.SingleClick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosePatientRecordAdapter extends BaseQuickAdapter<DiagnoseRecoderItemBean, BaseViewHolder> {
    private Context context;

    public DiagnosePatientRecordAdapter(Context context, List<DiagnoseRecoderItemBean> data) {
        super(R.layout.nursing_diagnosis_record_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DiagnoseRecoderItemBean item) {
        final Map<String, Long> map = new HashMap<>();
        if (item.getMedicalId() != 0) {
            map.put("medical", item.getMedicalId());
        }
        if (item.getInspectList() != null && item.getInspectList().size() > 0) {
            map.put("inspect", item.getInspectId());
        }
        if (item.getCheckoutList() != null && item.getCheckoutList().size() > 0) {
            map.put("checkout", item.getCheckoutId());
        }
        if (item.getPrescriptionId() != 0) {
            map.put("prescription", item.getPrescriptionId());
        }
        helper.setText(R.id.time_tv, item.getConsultTime())
                .setText(R.id.diagnosis_tv, "诊断:" + (item.getDiagnosis() == null ? "" : item.getDiagnosis()));
        final RelativeLayout itemLayout = helper.getView(R.id.item_layout);
        final View lineView = helper.getView(R.id.item_view);
        final ImageView openImg = helper.getView(R.id.open_img);
        final ImageView closeImg = helper.getView(R.id.close_img);
        final TextView time_tv = helper.getView(R.id.time_tv);
        final TextView function_medical_record_tv = helper.getView(R.id.function_medical_record_tv);
        function_medical_record_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //跳转到病例
                DianoseCaseDetailActivity.start(mContext, String.valueOf(item.getMedicalId()));
            }
        });
        final TextView function_inspect_tv = helper.getView(R.id.function_inspect_tv);
        function_inspect_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //跳转到检查
//                CheckOrderDetailActivity.startInspactOrder(mContext, item.getInspectId(), null);
//                BodyCheckDetailActivity.start(mContext, String.valueOf(item.getInspectId()));
                ReportListActivity.startInspectPage(mContext, item.getInspectList());
            }
        });
        final TextView function_check_tv = helper.getView(R.id.function_check_tv);
        function_check_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //跳转到检验
//                CheckOrderDetailActivity.startTestOrder(mContext, item.getCheckoutId(), null);
                ReportListActivity.startCheckOutPage(mContext, item.getCheckoutList());
//                InspectionDetailActivity.start(mContext, String.valueOf(item.getCheckoutId()));
            }
        });
        final TextView function_prescription_tv = helper.getView(R.id.function_prescription_tv);
        function_prescription_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                //跳转到处方
                PrescriptionActivity.startCommon(mContext, item.getPrescriptionId());
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
        itemLayout.measure(0, 0);
        layoutParams.height = itemLayout.getMeasuredHeight();
        lineView.setLayoutParams(layoutParams);
/*        openImg.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                itemLayout.setBackgroundColor(context.getResources().getColor(R.color.primary_bg_color));
                lineView.setVisibility(View.INVISIBLE);
                openImg.setVisibility(View.GONE);
                closeImg.setVisibility(View.VISIBLE);
                time_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.point_yellow), null, null, null);
                if (map.containsKey("medical")) {
                    function_medical_record_tv.setVisibility(View.VISIBLE);
                    function_medical_record_tv.setClickable(true);
                }
                if (map.containsKey("inspect")) {
                    function_inspect_tv.setVisibility(View.VISIBLE);
                    function_inspect_tv.setClickable(true);
                }
                if (map.containsKey("checkout")) {
                    function_check_tv.setVisibility(View.VISIBLE);
                    function_check_tv.setClickable(true);
                }
                if (map.containsKey("prescription")) {
                    function_prescription_tv.setVisibility(View.VISIBLE);
                    function_prescription_tv.setClickable(true);
                }
            }
        });*/
/*        closeImg.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                itemLayout.setBackgroundColor(context.getResources().getColor(R.color.login_input_color));
                lineView.setVisibility(View.VISIBLE);
                openImg.setVisibility(View.VISIBLE);
                closeImg.setVisibility(View.GONE);
                time_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.point_purple_blue), null, null, null);
                if (map.containsKey("medical")) {
                    function_medical_record_tv.setVisibility(View.GONE);
                    function_medical_record_tv.setClickable(false);
                }
                if (map.containsKey("inspect")) {
                    function_inspect_tv.setVisibility(View.GONE);
                    function_inspect_tv.setClickable(false);
                }
                if (map.containsKey("checkout")) {
                    function_check_tv.setVisibility(View.GONE);
                    function_check_tv.setClickable(false);
                }
                if (map.containsKey("prescription")) {
                    function_prescription_tv.setVisibility(View.GONE);
                    function_prescription_tv.setClickable(false);
                }
            }
        });*/


        itemLayout.setBackgroundColor(context.getResources().getColor(R.color.primary_bg_color));
        time_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.point_purple_blue), null, null, null);
        if (map.containsKey("medical")) {
            function_medical_record_tv.setVisibility(View.VISIBLE);
            function_medical_record_tv.setClickable(true);
        }
        if (map.containsKey("inspect")) {
            function_inspect_tv.setVisibility(View.VISIBLE);
            function_inspect_tv.setClickable(true);
        }
        if (map.containsKey("checkout")) {
            function_check_tv.setVisibility(View.VISIBLE);
            function_check_tv.setClickable(true);
        }
        if (map.containsKey("prescription")) {
            function_prescription_tv.setVisibility(View.VISIBLE);
            function_prescription_tv.setClickable(true);
        }

    }
}
