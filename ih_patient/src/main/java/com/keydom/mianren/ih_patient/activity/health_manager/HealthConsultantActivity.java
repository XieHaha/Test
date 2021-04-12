package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthConsultantController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthConsultantView;
import com.keydom.mianren.ih_patient.adapter.HealthConsultantAdapter;
import com.keydom.mianren.ih_patient.bean.HealthConsultantBean;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 21/4/8 15:37
 * @des 健康管理咨询师
 */
public class HealthConsultantActivity extends BaseControllerActivity<HealthConsultantController> implements HealthConsultantView {
    @BindView(R.id.health_consultant_recycler_view)
    RecyclerView healthConsultantRecyclerView;
    private HealthConsultantAdapter consultantAdapter;

    private String patientId;

    private ArrayList<HealthConsultantBean> consultantBeans = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context, String patientId) {
        Intent intent = new Intent(context, HealthConsultantActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_consultant;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("健康管理咨询师");
        patientId = getIntent().getStringExtra(Const.PATIENT_ID);
        consultantAdapter = new HealthConsultantAdapter(consultantBeans);
        consultantAdapter.setOnItemClickListener(getController());
        consultantAdapter.setOnItemChildClickListener(getController());
        healthConsultantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        healthConsultantRecyclerView.setAdapter(consultantAdapter);

        getController().healthDoctorList();
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public void requestHealthDoctorListSuccess(List<HealthConsultantBean> data) {
        consultantBeans.clear();
        consultantBeans.addAll(data);
        consultantAdapter.setNewData(consultantBeans);
    }
}
