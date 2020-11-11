package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.JustifiedTextView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisDetailController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisDetailView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/11 15:25
 * @des 羊水穿刺预约详情
 */
public class AmniocentesisDetailActivity extends BaseControllerActivity<AmniocentesisDetailController> implements AmniocentesisDetailView {
    @BindView(R.id.amniocentesis_detail_status_tv)
    TextView amniocentesisDetailStatusTv;
    @BindView(R.id.amniocentesis_detail_surgery_time_tv)
    TextView amniocentesisDetailSurgeryTimeTv;
    @BindView(R.id.amniocentesis_detail_name_tv)
    TextView amniocentesisDetailNameTv;
    @BindView(R.id.amniocentesis_detail_id_card_tv)
    TextView amniocentesisDetailIdCardTv;
    @BindView(R.id.amniocentesis_detail_birth_tv)
    TextView amniocentesisDetailBirthTv;
    @BindView(R.id.amniocentesis_detail_mine_phone_tv)
    TextView amniocentesisDetailMinePhoneTv;
    @BindView(R.id.amniocentesis_detail_last_menstruation_tv)
    TextView amniocentesisDetailLastMenstruationTv;
    @BindView(R.id.amniocentesis_detail_due_date_tv)
    TextView amniocentesisDetailDueDateTv;
    @BindView(R.id.amniocentesis_detail_family_name_tv)
    TextView amniocentesisDetailFamilyNameTv;
    @BindView(R.id.amniocentesis_detail_family_phone_tv)
    TextView amniocentesisDetailFamilyPhoneTv;
    @BindView(R.id.amniocentesis_detail_family_address_tv)
    TextView amniocentesisDetailFamilyAddressTv;
    @BindView(R.id.amniocentesis_detail_hospital_tv)
    TextView amniocentesisDetailHospitalTv;
    @BindView(R.id.amniocentesis_detail_reason_tv)
    TextView amniocentesisDetailReasonTv;
    @BindView(R.id.amniocentesis_detail_fetus_tv)
    TextView amniocentesisDetailFetusTv;
    @BindView(R.id.amniocentesis_detail_hiv_tv)
    TextView amniocentesisDetailHivTv;
    @BindView(R.id.amniocentesis_detail_blood_tv)
    TextView amniocentesisDetailBloodTv;
    @BindView(R.id.amniocentesis_detail_hypertension_tv)
    TextView amniocentesisDetailHypertensionTv;
    @BindView(R.id.amniocentesis_detail_diabetes_tv)
    TextView amniocentesisDetailDiabetesTv;
    @BindView(R.id.amniocentesis_detail_next_tv)
    TextView amniocentesisDetailNextTv;
    @BindView(R.id.amniocentesis_detail_syphilis_tv)
    TextView amniocentesisDetailSyphilisTv;
    @BindView(R.id.amniocentesis_detail_ultrasound_tv)
    TextView amniocentesisDetailUltrasoundTv;
    @BindView(R.id.amniocentesis_detail_nt_tv)
    TextView amniocentesisDetailNtTv;
    @BindView(R.id.amniocentesis_detail_headlength_tv)
    TextView amniocentesisDetailHeadlengthTv;
    @BindView(R.id.amniocentesis_detail_ultrasound_date_tv)
    TextView amniocentesisDetailUltrasoundDateTv;
    @BindView(R.id.amniocentesis_detail_cancel_reason_tv)
    JustifiedTextView amniocentesisDetailCancelReasonTv;
    @BindView(R.id.amniocentesis_detail_cancel_reason_layout)
    LinearLayout amniocentesisDetailCancelReasonLayout;

    private AmniocentesisBean amniocentesisBean;

    public static final String AMNIOCENTESIS_BEAN = "amniocentesis_bean";

    public static void start(Context context, AmniocentesisBean bean) {
        Intent intent = new Intent(context, AmniocentesisDetailActivity.class);
        intent.putExtra(AMNIOCENTESIS_BEAN, bean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_amniocentesis_record_cancel));

        if (getIntent() != null) {
            amniocentesisBean =
                    (AmniocentesisBean) getIntent().getSerializableExtra(AMNIOCENTESIS_BEAN);
        }

        //        getController().getAmniocentesisDetail(amniocentesisId);

        amniocentesisDetailNextTv.setOnClickListener(v -> finish());
        //        setReloadListener((v, status) -> {
        //            getController().getAmniocentesisDetail(amniocentesisId);
        //        });

        bindData();
    }

    /**
     * 数据绑定
     */
    private void bindData() {
        if (amniocentesisBean == null) {
            return;
        }
        switch (amniocentesisBean.getState()) {
            case "-1":
                amniocentesisDetailStatusTv.setText("已取消");
                amniocentesisDetailCancelReasonLayout.setVisibility(View.VISIBLE);
                break;
            case "0":
                amniocentesisDetailStatusTv.setText("未预约");
                break;
            case "1":
                amniocentesisDetailStatusTv.setText("待确认");
                break;
            case "2":
                amniocentesisDetailStatusTv.setText("已确认");
                break;
            default:
        }
        amniocentesisDetailSurgeryTimeTv.setText(DateUtils.transDate(amniocentesisBean.getSurgeryTime(),
                DateUtils.YYYY_MM_DD, DateUtils.YYYY_MM_DD_CH));
        amniocentesisDetailNameTv.setText(amniocentesisBean.getName());
        amniocentesisDetailIdCardTv.setText(amniocentesisBean.getIdCard());
        amniocentesisDetailBirthTv.setText(DateUtils.transDate(amniocentesisBean.getBirthday(),
                DateUtils.YYYY_MM_DD, DateUtils.YYYY_MM_DD_CH));
        amniocentesisDetailMinePhoneTv.setText(amniocentesisBean.getTelephone());
        amniocentesisDetailLastMenstruationTv.setText(DateUtils.transDate(amniocentesisBean.getEndMensesTime(),
                DateUtils.YYYY_MM_DD, DateUtils.YYYY_MM_DD_CH));
        //        amniocentesisDetailDueDateTv.setText(DateUtils.transDate(amniocentesisBean
        //        .getExpectedBirthTime(),
        //                DateUtils.YYYY_MM_DD, DateUtils.YYYY_MM_DD_CH));
        amniocentesisDetailFamilyNameTv.setText(amniocentesisBean.getFamilyMemberName());
        amniocentesisDetailFamilyPhoneTv.setText(amniocentesisBean.getFamilyMemberPhone());
        amniocentesisDetailFamilyAddressTv.setText(amniocentesisBean.getFamilyAddress());
        amniocentesisDetailHospitalTv.setText(amniocentesisBean.getReferralHospital());
        amniocentesisDetailReasonTv.setText(amniocentesisBean.getReason());
        amniocentesisDetailSyphilisTv.setText(amniocentesisBean.getSyphilis());
        amniocentesisDetailNtTv.setText(amniocentesisBean.getNt());
        amniocentesisDetailHeadlengthTv.setText(amniocentesisBean.getHeadLength());
        amniocentesisDetailUltrasoundDateTv.setText(amniocentesisBean.getUltrasonicDate());
        amniocentesisDetailCancelReasonTv.setText(amniocentesisBean.getRefusedReason());
        int fetus = amniocentesisBean.getFetusNum();
        switch (fetus) {
            case 1:
                amniocentesisDetailFetusTv.setText(R.string.txt_one_fetus);
                break;
            case 2:
                amniocentesisDetailFetusTv.setText(R.string.txt_two_fetus);
                break;
            case 3:
                amniocentesisDetailFetusTv.setText(R.string.txt_more_fetus);
                break;
            default:
                amniocentesisDetailFetusTv.setText(R.string.txt_one_fetus);
                break;
        }
        amniocentesisDetailHivTv.setText(amniocentesisBean.getHivAttribute());
        amniocentesisDetailBloodTv.setText(amniocentesisBean.getRhAttribute());
        amniocentesisDetailUltrasoundTv.setText(TextUtils.equals(amniocentesisBean.getIsUltrasonicException(), "1") ? R.string.txt_yes : R.string.txt_no);
        amniocentesisDetailHypertensionTv.setText(TextUtils.equals(amniocentesisBean.getIsHypertension(), "1") ? R.string.txt_have : R.string.txt_none);
        amniocentesisDetailDiabetesTv.setText(TextUtils.equals(amniocentesisBean.getIsGlycuresis(), "1") ? R.string.txt_have : R.string.txt_none);
    }

    @Override
    public void onAmniocentesisDetailSuccess() {
        bindData();
    }

    @Override
    public void onAmniocentesisDetailFailed() {
        pageLoadingFail();
    }
}
