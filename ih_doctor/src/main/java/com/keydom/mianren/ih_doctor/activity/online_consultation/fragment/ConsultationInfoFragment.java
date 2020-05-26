package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ImageAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationInfoController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationInfoView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @date 20/4/8 14:33
 * @des 会诊室-病历资料
 */
public class ConsultationInfoFragment extends BaseControllerFragment<ConsultationInfoController> implements ConsultationInfoView {
    @BindView(R.id.consultation_info_chief_item)
    DiagnosePrescriptionItemView consultationInfoChiefItem;
    @BindView(R.id.consultation_info_purpose_item)
    DiagnosePrescriptionItemView consultationInfoPurposeItem;
    @BindView(R.id.consultation_info_summary_item)
    DiagnosePrescriptionItemView consultationInfoSummaryItem;
    @BindView(R.id.consultation_info_condition_image_grid)
    GridViewForScrollView consultationInfoConditionImageGrid;
    @BindView(R.id.consultation_info_apply_layout)
    LinearLayout consultationInfoApplyLayout;
    @BindView(R.id.consultation_info_medical_layout)
    LinearLayout consultationInfoMedicalLayout;
    @BindView(R.id.consultation_info_report_layout)
    LinearLayout consultationInfoReportLayout;
    @BindView(R.id.consultation_info_video_layout)
    LinearLayout consultationInfoVideoLayout;
    @BindView(R.id.consultation_info_patient_header)
    ImageView consultationInfoPatientHeader;
    @BindView(R.id.consultation_info_patient_name)
    TextView consultationInfoPatientName;
    @BindView(R.id.consultation_info_patient_age)
    TextView consultationInfoPatientAge;
    @BindView(R.id.consultation_info_patient_sex)
    TextView consultationInfoPatientSex;
    @BindView(R.id.consultation_info_chief)
    TextView consultationInfoChief;
    @BindView(R.id.consultation_info_patient_image)
    GridViewForScrollView consultationInfoPatientImageGrid;

    private String orderId, inquiryId;

    private ConsultationDetailBean infoBean;

    public static ConsultationInfoFragment newInstance(String id, String inquiryId) {
        ConsultationInfoFragment fragment = new ConsultationInfoFragment();
        Bundle args = new Bundle();
        args.putString(Const.ORDER_ID, id);
        args.putString("inquiryId", inquiryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        orderId = bundle.getString(Const.ORDER_ID);
        inquiryId = bundle.getString("inquiryId");
        consultationInfoApplyLayout.setOnClickListener(getController());
        consultationInfoMedicalLayout.setOnClickListener(getController());
        consultationInfoReportLayout.setOnClickListener(getController());
        consultationInfoVideoLayout.setOnClickListener(getController());

        getController().getConsultationOrderDetail(orderId);
        getController().getPatientInquisitionById(inquiryId);
    }

    /**
     * 病历资料信息
     */
    private void bindData() {
        if (infoBean != null) {
            consultationInfoChiefItem.setText(infoBean.getMainTell());
            consultationInfoPurposeItem.setText(infoBean.getReasonAndAim());
            consultationInfoSummaryItem.setText(infoBean.getIllnessAbstract());

            //图片适配器，病情资料和问诊说明图片适配器
            ImageAdapter imageAdapter = new ImageAdapter(getContext(),
                    infoBean.getMedicalHistoryImg() == null ? new ArrayList<>() :
                            infoBean.getMedicalHistoryImg(), false);
            consultationInfoConditionImageGrid.setAdapter(imageAdapter);
        }
    }

    /**
     * 患者问诊单信息
     */
    private void setInfo(DiagnoseOrderDetailBean bean) {
        GlideUtils.load(consultationInfoPatientHeader,
                BaseFileUtils.getHeaderUrl(bean.getUserAvatar()), 0, R.mipmap.im_default_head_image,
                true, null);
        consultationInfoPatientName.setText(bean.getName());
        consultationInfoPatientAge.setText(bean.getAge());
        consultationInfoPatientSex.setText(CommonUtils.getSex(bean.getSex()));
        consultationInfoChief.setText(bean.getConditionDesc());

        if (!TextUtils.isEmpty(bean.getConditionData())) {
            String[] imageDesc = bean.getConditionData().split(",");
            List<String> images = new ArrayList<>();
            Collections.addAll(images, imageDesc);
            //问诊说明图片适配器
            ImageAdapter imageAdapter = new ImageAdapter(getContext(), images, false);
            consultationInfoPatientImageGrid.setAdapter(imageAdapter);
        }
    }

    @Override
    public void onApplyInfoSelect() {
        indexSelected(0);
    }

    @Override
    public void onMedicalSelect() {
        indexSelected(1);
    }

    @Override
    public void onReportInfoSelect() {
        indexSelected(2);
    }

    @Override
    public void onVideoSelect() {
        indexSelected(3);
    }

    private void indexSelected(int index) {
        switch (index) {
            case 0:
                consultationInfoApplyLayout.setSelected(true);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 1:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(true);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 2:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(true);
                consultationInfoVideoLayout.setSelected(false);
                break;
            case 3:
                consultationInfoApplyLayout.setSelected(false);
                consultationInfoMedicalLayout.setSelected(false);
                consultationInfoReportLayout.setSelected(false);
                consultationInfoVideoLayout.setSelected(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void requestInfoSuccess(ConsultationDetailBean bean) {
        infoBean = bean;
        bindData();
    }

    @Override
    public void requestInfoFailed(String error) {
        ToastUtil.showMessage(getContext(), error);
    }

    @Override
    public void requestInquisitionSuccess(DiagnoseOrderDetailBean bean) {
        setInfo(bean);
    }

    @Override
    public void requestInquisitionFailed(String error) {
        ToastUtil.showMessage(getContext(), error);
    }
}
