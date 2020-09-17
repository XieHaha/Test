package com.keydom.mianren.ih_patient.activity.prescription_check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.prescription_check.controller.PrescriptionRecordController;
import com.keydom.mianren.ih_patient.activity.prescription_check.view.PrescriptionRecordView;
import com.keydom.mianren.ih_patient.adapter.PrescriptionRecordAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PrescriptionRootBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * created date: 2019/1/4 on 13:00
 * des:处方记录页面
 *
 * @author 顿顿
 */
public class PrescriptionRecordActivity extends BaseControllerActivity<PrescriptionRecordController> implements PrescriptionRecordView {
    @BindView(R.id.medical_record_name_tv)
    TextView medicalRecordNameTv;
    @BindView(R.id.medical_record_card_number_tv)
    TextView medicalRecordCardNumberTv;
    @BindView(R.id.medical_record_patient_layout)
    RelativeLayout medicalRecordPatientLayout;
    @BindView(R.id.medical_record_start_date_tv)
    TextView startDateTv;
    @BindView(R.id.medical_record_start_date_layout)
    LinearLayout startDateLayout;
    @BindView(R.id.medical_record_end_date_tv)
    TextView endDateTv;
    @BindView(R.id.medical_record_end_date_layout)
    LinearLayout endDateLayout;
    @BindView(R.id.medical_record_date_layout)
    LinearLayout medicalRecordDateLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private PrescriptionRecordAdapter mAdapter;

    private PrescriptionRootBean rootBean;
    private MedicalCardInfo medicalCardInfo;

    private String startDate, endDate;

    public static void start(Context context, MedicalCardInfo cardInfo) {
        Intent intent = new Intent(context, PrescriptionRecordActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("处方单");
        medicalCardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);

        initDefaultDate();
        startDateTv.setText(startDate);
        endDateTv.setText(endDate);
        medicalRecordNameTv.setText(medicalCardInfo.getName());
        medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());
        startDateLayout.setOnClickListener(getController());
        endDateLayout.setOnClickListener(getController());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PrescriptionRecordAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(PrescriptionRecordActivity.this,
                    PrescriptionDetailActivity.class);
            intent.putExtra(Const.DATA, rootBean);
            intent.putExtra(Const.POSITION, position);
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getPrescriptionList());

        getController().getPrescriptionList();
    }

    /**
     * 初始化时间范围
     */
    private void initDefaultDate() {
        Date date = new Date();
        startDate = DateUtils.getInterValDate(date, -1);
        endDate = DateUtils.dateToString(date);
    }

    @Override
    public Map<String, Object> getParams() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardNo", medicalCardInfo.getEleCardNumber());
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    @Override
    public void setStartDate(Date date) {
        startDate = DateUtils.dateToString(date);
        startDateTv.setText(startDate);
        getController().getPrescriptionList();
    }

    @Override
    public void setEndDate(Date date) {
        endDate = DateUtils.dateToString(date);
        endDateTv.setText(endDate);
        getController().getPrescriptionList();
    }


    @Override
    public void getRecordList(PrescriptionRootBean bean) {
        refreshLayout.finishRefresh();
        rootBean = bean;
        mAdapter.replaceData(rootBean.getItem());
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }
}
