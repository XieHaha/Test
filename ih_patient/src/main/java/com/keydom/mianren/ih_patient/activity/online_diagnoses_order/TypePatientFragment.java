package com.keydom.mianren.ih_patient.activity.online_diagnoses_order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller.TypePatientController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.TypePatientView;
import com.keydom.mianren.ih_patient.adapter.ChoosePatientAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TypePatientFragment extends BaseControllerFragment<TypePatientController> implements TypePatientView {
    private TextView jump_to_patient_operate_tv, registerDoctorCommitTv,add_label_tv;
    private RecyclerView choose_medical_card_rv;
    private ChoosePatientAdapter choosePatientAdapter;
    private List<ManagerUserBean> dataList = new ArrayList<>();
    private ManagerUserBean selectedPatientInfo=null;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_type_patient_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        add_label_tv=getView().findViewById(R.id.add_label_tv);
        jump_to_patient_operate_tv = getView().findViewById(R.id.jump_to_patient_operate_tv);
        jump_to_patient_operate_tv.setOnClickListener(getController());
        registerDoctorCommitTv = getView().findViewById(R.id.commit_tv);
        registerDoctorCommitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPatientInfo!=null){
                    EventBus.getDefault().post(new Event(EventType.SENDSELECTDIAGNOSESPATIENT,selectedPatientInfo));
                    getActivity().finish();
                }else {
                    ToastUtil.showMessage(getContext(),"请选择就诊卡");
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
        choosePatientAdapter = new ChoosePatientAdapter(getContext(), dataList, new GeneralCallback.SelectPatientListener() {
            @Override
            public void getSelectedPatient(ManagerUserBean managerUserBean) {
                selectedPatientInfo=managerUserBean;
            }
        });
        choose_medical_card_rv.setAdapter(choosePatientAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().getManagerUserList();
    }
    @Override
    public void getMangerUserList(List<ManagerUserBean> data) {
        dataList.clear();
        dataList.addAll(data);
        choosePatientAdapter.notifyDataSetChanged();

    }
}
