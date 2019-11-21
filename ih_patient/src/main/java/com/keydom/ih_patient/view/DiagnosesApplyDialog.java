package com.keydom.ih_patient.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.ih_patient.bean.CommonDocumentBean;

/**
 * 问诊订单对话框
 */
public class DiagnosesApplyDialog extends Dialog implements View.OnClickListener {
    public static final String PHOTODIAGNOSES = "photo_diagnoses";
    public static final String VIDEODIAGNOSES = "video_diagnoses";
    private Context context;
    private TextView applu_fee_tv,discount_tv,service_label;
    private TextView doctor_name_tv;
    private TextView apply_type_tv;
    private TextView wait_num_tv;
    private TextView notice_tv;
    private TextView canncel_apply_tv;
    private TextView commit_apply_tv;
    private CheckBox notice_ck;
    private String fee, doctorName, diagnosesType;
    private float disCount;
    private int waitNum;
    private OnCommitListener onCommitListener;
    private boolean isAgreeNotice = false;
    private ImageView icon_f;

    /**
     * 构建方法
     */
    public DiagnosesApplyDialog(@NonNull Context context, String fee,float disCount, String doctorName, int waitNum, String diagnosesType, OnCommitListener onCommitListener) {
        super(context,com.keydom.ih_common.R.style.dialog);
        this.context = context;
        this.fee = fee;
        this.disCount=disCount;
        this.doctorName = doctorName;
        this.waitNum = waitNum;
        this.diagnosesType = diagnosesType;
        this.onCommitListener = onCommitListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnoses_apply_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        icon_f=findViewById(R.id.icon_f);
        applu_fee_tv = findViewById(R.id.applu_fee_tv);
        discount_tv=findViewById(R.id.discount_tv);
        if(disCount<1.0){
            float resultFee=Float.valueOf(fee)*disCount;
            String feeStr= String.format("%.2f",resultFee);
            applu_fee_tv.setText(fee != null ? "¥" + feeStr+ "/次":"" );
            discount_tv.setVisibility(View.VISIBLE);
            discount_tv.setText("复诊患者首次问诊费用"+disCount*10+"折优惠");
        }else {
            applu_fee_tv.setText(fee != null ? "¥" + fee+ "/次":"" );
            discount_tv.setVisibility(View.GONE);
        }
        doctor_name_tv = findViewById(R.id.doctor_name_tv);
        doctor_name_tv.setText(doctorName != null ? doctorName : "");
        apply_type_tv = findViewById(R.id.apply_type_tv);
        if (PHOTODIAGNOSES.equals(diagnosesType)) {
            icon_f.setImageResource(R.mipmap.dialog_pic_icon);
            apply_type_tv.setText("通过图文进行问诊咨询");
        } else if (VIDEODIAGNOSES.equals(diagnosesType)) {
            icon_f.setImageResource(R.mipmap.dialog_video_icon);
            apply_type_tv.setText("通过视频进行问诊咨询");
        }
        wait_num_tv = findViewById(R.id.wait_num_tv);
        wait_num_tv.setText("前面排队待诊人数：" + waitNum + "人");
        notice_tv = findViewById(R.id.notice_tv);
        notice_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AgreementActivity.startOnLineDiagnoseAgreement(context);
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_1);
            }
        });
        service_label=findViewById(R.id.service_label);
        service_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_14);

            }
        });
        canncel_apply_tv = findViewById(R.id.canncel_apply_tv);
        canncel_apply_tv.setOnClickListener(this);
        commit_apply_tv = findViewById(R.id.commit_apply_tv);
        commit_apply_tv.setOnClickListener(this);
        notice_ck = findViewById(R.id.notice_ck);
        notice_ck.setOnCheckedChangeListener((compoundButton, b) -> {
            isAgreeNotice = b;
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.canncel_apply_tv) {
            this.dismiss();

        } else if (i == R.id.commit_apply_tv) {
            if (isAgreeNotice) {
                if (onCommitListener != null) {
                    onCommitListener.onCommit();
                }
                this.dismiss();
            } else
                ToastUtil.showMessage(context, "请先阅读并同意在线问诊用户协议");

        }
    }

    /**
     * 提交监听
     */
    public interface OnCommitListener {
        void onCommit();
    }
}
