package com.keydom.mianren.ih_patient.activity.medical_record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.medical_record.controller.MedicalRecordController;
import com.keydom.mianren.ih_patient.activity.medical_record.view.MedicalRecordView;
import com.keydom.mianren.ih_patient.adapter.MedicalRecordAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.MedicalRecordBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private MedicalRecordAdapter mAdapter;

    private String startDate, endDate;

    private MedicalCardInfo medicalCardInfo;

    public static void start(Context context, MedicalCardInfo cardInfo) {
        Intent intent = new Intent(context, MedicalRecordActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("门诊病历");
        medicalCardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);

        initDefaultDate();
        startDateTv.setText(startDate);
        endDateTv.setText(endDate);
        medicalRecordNameTv.setText(medicalCardInfo.getName());
        medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());
        startDateLayout.setOnClickListener(getController());
        endDateLayout.setOnClickListener(getController());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MedicalRecordAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            MedicalRecordBean bean = (MedicalRecordBean) adapter.getData().get(position);
            Intent i = new Intent(this, MedicalRecordDetailActivity.class);
            i.putExtra(MedicalRecordDetailActivity.MEDICAL_ID, bean.getMedicalId());
            ActivityUtils.startActivity(i);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getIndAllData());

        getController().getIndAllData();
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
        map.put("deptCode", "");
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        //1、门诊病历   2、住院病历
        map.put("type", 1);
        return map;
    }

    @Override
    public void setStartDate(Date date) {
        startDate = DateUtils.dateToString(date);
        startDateTv.setText(startDate);
        getController().getIndAllData();
    }

    @Override
    public void setEndDate(Date date) {
        endDate = DateUtils.dateToString(date);
        endDateTv.setText(endDate);
        getController().getIndAllData();
    }


    @Override
    public void getRecordList(List<MedicalRecordBean> list) {
        refreshLayout.finishRefresh();
        pageLoadingSuccess();
        mAdapter.replaceData(list);
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }
}
