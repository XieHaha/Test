package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.widget.AutoGridView;
import com.keydom.ih_common.utils.BaseImageUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationDoctorAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationReceiveController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationReceiveView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDoctorBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/27 14:38
 * @des 会诊接收
 */
public class ConsultationReceiveActivity extends BaseControllerActivity<ConsultationReceiveController> implements ConsultationReceiveView {
    @BindView(R.id.consultation_receive_header_iv)
    ImageView consultationReceiveHeaderIv;
    @BindView(R.id.consultation_receive_patient_name_tv)
    TextView consultationReceivePatientNameTv;
    @BindView(R.id.consultation_receive_patient_sex_tv)
    TextView consultationReceivePatientSexTv;
    @BindView(R.id.consultation_receive_patient_age_tv)
    TextView consultationReceivePatientAgeTv;
    @BindView(R.id.consultation_receive_level_tv)
    TextView consultationReceiveLevelTv;
    @BindView(R.id.consultation_receive_card_tv)
    TextView consultationReceiveCardTv;
    @BindView(R.id.consultation_receive_visit_time_tv)
    TextView consultationReceiveVisitTimeTv;
    @BindView(R.id.consultation_receive_apply_doctor_header_iv)
    ImageView consultationReceiveApplyDoctorHeaderIv;
    @BindView(R.id.consultation_receive_apply_doctor_name_tv)
    TextView consultationReceiveApplyDoctorNameTv;
    @BindView(R.id.consultation_receive_consultation_doctor_grid_view)
    AutoGridView consultationReceiveConsultationDoctorGridView;
    @BindView(R.id.consultation_receive_consultation_date_tv)
    TextView consultationReceiveConsultationDateTv;
    @BindView(R.id.consultation_receive_consultation_week_tv)
    TextView consultationReceiveConsultationWeekTv;
    @BindView(R.id.consultation_receive_consultation_time_tv)
    TextView consultationReceiveConsultationTimeTv;
    @BindView(R.id.consultation_receive_purpose_item)
    DiagnosePrescriptionItemView consultationReceivePurposeItem;
    @BindView(R.id.consultation_receive_summary_item)
    DiagnosePrescriptionItemView consultationReceiveSummaryItem;
    @BindView(R.id.consultation_receive_information_item)
    DiagnosePrescriptionItemView consultationReceiveInformationItem;
    @BindView(R.id.consultation_receive_advice_item)
    DiagnosePrescriptionItemView consultationReceiveAdviceItem;

    /**
     * 会诊医生
     */
    private ConsultationDoctorAdapter doctorAdapter;

    private ConsultationDetailBean detailBean;

    private String orderId;

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ConsultationReceiveActivity.class);
        intent.putExtra(Const.ORDER_ID, id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_receive;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_consultation_receive));
        orderId = getIntent().getStringExtra(Const.ORDER_ID);

        doctorAdapter = new ConsultationDoctorAdapter(this);
        consultationReceiveConsultationDoctorGridView.setAdapter(doctorAdapter);

        pageLoading();
        getController().getConsultationOrderDetail(orderId);
        getController().consultationOrderAdviceList(orderId);
    }

    /**
     * 页面数据绑定
     */
    private void bindData() {
        if (detailBean != null) {
            int status = detailBean.getStatus();
            if (status == 0) {
                setRightTxt(getString(R.string.txt_receive));
                setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
                    @Override
                    public void OnRightTextClick(View v) {
                        ToastUtil.showMessage(ConsultationReceiveActivity.this, "接收");
                    }
                });
            } else {
                setRightTxt("");
            }
            //会诊医生信息
            doctorAdapter.setData(detailBean.getMdtDoctors());
            //患者基础信息
            consultationReceivePatientNameTv.setText(detailBean.getPatientName());
            consultationReceivePatientSexTv.setText(CommonUtils.getPatientSex(detailBean.getPatientGender()));
            consultationReceivePatientAgeTv.setText(String.format(getString(R.string.txt_age),
                    detailBean.getPatientAge()));
            GlideUtils.load(consultationReceiveHeaderIv,
                    BaseImageUtils.getHeaderUrl(detailBean.getRegisterUserImage()), 0,
                    R.mipmap.im_default_head_image, true, null);
            if (detailBean.getLevel() == 0) {
                consultationReceiveLevelTv.setBackgroundResource(R.drawable.corner5_fbd54e_bg);
                consultationReceiveLevelTv.setText("普通");
            } else {
                consultationReceiveLevelTv.setBackgroundResource(R.drawable.corner5_ff3939_bg);
                consultationReceiveLevelTv.setText("紧急");
            }
            consultationReceiveConsultationDateTv.setText(DateUtils.getDate(detailBean.getVisitTime()));
            //申请医生信息
            ConsultationDoctorBean bean = detailBean.getApplyDoctor();
            if (bean != null) {
                GlideUtils.load(consultationReceiveApplyDoctorHeaderIv,
                        BaseImageUtils.getHeaderUrl(bean.getDoctorImage()), 0,
                        R.mipmap.im_default_head_image, true, null);
                consultationReceiveApplyDoctorNameTv.setText(bean.getName());
            }
            consultationReceiveConsultationDateTv.setText(detailBean.getMdtTime());
        }
    }

    @Override
    public void requestDetailSuccess(ConsultationDetailBean data) {
        detailBean = data;
        bindData();
        pageLoadingSuccess();
    }

    @Override
    public void requestDetailFailed(String msg) {
        pageLoadingFail();
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public void requestAdviceSuccess() {

    }

    @Override
    public void requestAdviceFailed(String msg) {

    }
}
