package com.keydom.ih_patient.activity.online_diagnoses_order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.TypeCardController;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.TypeCardView;
import com.keydom.ih_patient.activity.pregnancy.PregnancyActivity;
import com.keydom.ih_patient.adapter.RegistrationCardAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择就诊卡页面
 */
public class TypeCardFragment extends BaseControllerFragment<TypeCardController> implements TypeCardView {
    private TextView jump_to_card_operate_tv, registerDoctorCommitTv;
    private RecyclerView choose_medical_card_rv;
    private RegistrationCardAdapter registrationCardAdapter;
    private List<MedicalCardInfo> dataList = new ArrayList<>();
    private MedicalCardInfo selectedCardInfo = null;

    private boolean isNeedToPregnancy = false;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_type_card_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isNeedToPregnancy = getArguments().getBoolean(Const.IS_NEED_TO_PREGNANCY, false);
        jump_to_card_operate_tv = getView().findViewById(R.id.jump_to_card_operate_tv);
        jump_to_card_operate_tv.setOnClickListener(getController());
        registerDoctorCommitTv = getView().findViewById(R.id.commit_tv);
        registerDoctorCommitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCardInfo != null) {
                    if (isNeedToPregnancy) {
                        PregnancyActivity.start(getActivity(),selectedCardInfo);
                        getActivity().finish();
                    } else {
                        EventBus.getDefault().post(new Event(EventType.SENDSELECTNURSINGPATIENT, selectedCardInfo));
                        getActivity().finish();
                    }

                } else {
                    ToastUtil.showMessage(getContext(), "请选择就诊卡");
                }
            }
        });
        choose_medical_card_rv = getView().findViewById(R.id.choose_medical_card_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        choose_medical_card_rv.setLayoutManager(layoutManager);
        choose_medical_card_rv.setHasFixedSize(true);
        choose_medical_card_rv.setNestedScrollingEnabled(false);
        registrationCardAdapter = new RegistrationCardAdapter(getContext(), dataList, new GeneralCallback.SelectCardListener() {
            @Override
            public void getSelectedCard(MedicalCardInfo medicalCardInfo) {
                selectedCardInfo = medicalCardInfo;
            }
        });
        choose_medical_card_rv.setAdapter(registrationCardAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().queryAllCard();
    }

    @Override
    public void getAllCardSuccess(List<MedicalCardInfo> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        registrationCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllCardFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "接口异常：" + errMsg);
    }
}
