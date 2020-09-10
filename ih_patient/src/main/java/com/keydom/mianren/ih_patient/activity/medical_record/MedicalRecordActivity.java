package com.keydom.mianren.ih_patient.activity.medical_record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralItemView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_record.controller.MedicalRecordController;
import com.keydom.mianren.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

/**
 * created date: 2019/1/4 on 13:00
 * des:门诊记录页面
 *
 * @author 顿顿
 */
public class MedicalRecordActivity extends BaseControllerActivity<MedicalRecordController> implements MedicalRecordView {
    @BindView(R.id.medical_record_name_tv)
    TextView medicalRecordNameTv;
    @BindView(R.id.medical_record_card_number_tv)
    TextView medicalRecordCardNumberTv;
    @BindView(R.id.medical_record_change_tv)
    TextView medicalRecordChangeTv;
    @BindView(R.id.medical_record_inspection)
    GeneralItemView medicalRecordInspection;
    @BindView(R.id.medical_record_medical)
    GeneralItemView medicalRecordMedical;
    @BindView(R.id.medical_record_examine)
    GeneralItemView medicalRecordExamine;
    @BindView(R.id.medical_record_check)
    GeneralItemView medicalRecordCheck;
    @BindView(R.id.medical_record_prescription)
    GeneralItemView medicalRecordPrescription;

    /**
     * 就诊卡
     */
    private MedicalCardInfo medicalCardInfo;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MedicalRecordActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("门诊病历查询");
        EventBus.getDefault().register(this);

        medicalRecordChangeTv.setOnClickListener(getController());
        medicalRecordInspection.setOnClickListener(getController());
        medicalRecordMedical.setOnClickListener(getController());
        medicalRecordExamine.setOnClickListener(getController());
        medicalRecordCheck.setOnClickListener(getController());
        medicalRecordPrescription.setOnClickListener(getController());

        getController().queryAllCard();
    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            medicalRecordNameTv.setText(medicalCardInfo.getName());
            medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());
        }
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> data) {
        if (data != null && data.size() > 0) {
            medicalCardInfo = data.get(0);
            medicalRecordNameTv.setText(medicalCardInfo.getName());
            medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());
        }
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
