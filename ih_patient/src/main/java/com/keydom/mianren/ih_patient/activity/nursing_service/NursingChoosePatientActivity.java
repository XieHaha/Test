package com.keydom.mianren.ih_patient.activity.nursing_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_service.controller.NursingChoosePatientController;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.NursingChoosePatientView;
import com.keydom.mianren.ih_patient.adapter.RegistrationCardAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择服务对象页面
 */
public class NursingChoosePatientActivity extends BaseControllerActivity<NursingChoosePatientController> implements NursingChoosePatientView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, NursingChoosePatientActivity.class));
    }

    private TextView jump_to_card_operate_tv, registerDoctorCommitTv;
    private RecyclerView choose_medical_card_rv;
    private RegistrationCardAdapter registrationCardAdapter;
    private List<MedicalCardInfo> dataList = new ArrayList<>();
    private MedicalCardInfo selectedCardInfo=null;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_choose_patient_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("选择就诊卡");

        jump_to_card_operate_tv = this.findViewById(R.id.jump_to_card_operate_tv);
        jump_to_card_operate_tv.setOnClickListener(getController());
        registerDoctorCommitTv = findViewById(R.id.commit_tv);
        registerDoctorCommitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCardInfo!=null){
                    EventBus.getDefault().post(new Event(EventType.SENDSELECTNURSINGPATIENT,selectedCardInfo));
                    finish();
                }else {
                    ToastUtil.showMessage(getContext(),"请选择就诊卡");
                }
            }
        });
        choose_medical_card_rv = this.findViewById(R.id.choose_medical_card_rv);
        registrationCardAdapter = new RegistrationCardAdapter(getContext(), dataList, new GeneralCallback.SelectCardListener() {
            @Override
            public void getSelectedCard(MedicalCardInfo medicalCardInfo) {
                selectedCardInfo=medicalCardInfo;
            }
        });
        choose_medical_card_rv.setAdapter(registrationCardAdapter);
    }

    @Override
    protected void onResume() {
        getController().queryAllCard();
        super.onResume();
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        registrationCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllCardFailed(String errMsg) {

    }
}
