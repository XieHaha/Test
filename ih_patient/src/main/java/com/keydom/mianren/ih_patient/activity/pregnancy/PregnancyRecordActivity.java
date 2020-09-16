package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyRecordController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyRecordView;
import com.keydom.mianren.ih_patient.adapter.PregnancyRecordAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created date: 2019/1/4 on 13:00
 * des:产检病历页面
 *
 * @author 顿顿
 */
public class PregnancyRecordActivity extends BaseControllerActivity<PregnancyRecordController> implements PregnancyRecordView {
    @BindView(R.id.medical_record_date_layout)
    LinearLayout medicalRecordDateLayout;
    @BindView(R.id.medical_record_name_tv)
    TextView medicalRecordNameTv;
    @BindView(R.id.medical_record_card_number_tv)
    TextView medicalRecordCardNumberTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    private PregnancyRecordAdapter mAdapter;

    private MedicalCardInfo medicalCardInfo;

    public static void start(Context context, MedicalCardInfo cardInfo) {
        Intent intent = new Intent(context, PregnancyRecordActivity.class);
        intent.putExtra(Const.DATA, cardInfo);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("产检病历");
        medicalCardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);

        medicalRecordDateLayout.setVisibility(View.GONE);
        medicalRecordNameTv.setText(medicalCardInfo.getName());
        medicalRecordCardNumberTv.setText(medicalCardInfo.getEleCardNumber());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PregnancyRecordAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PregnancyRecordBean bean = (PregnancyRecordBean) adapter.getData().get(position);
            PregnancyRecordDetailActivity.start(PregnancyRecordActivity.this, bean);
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getIndAllData());

        getController().getIndAllData();
    }

    @Override
    public void requestRecordSuccess(List<PregnancyRecordBean> list) {
        refreshLayout.finishRefresh();
        if (list == null || list.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            mAdapter.replaceData(list);
        }
    }

    @Override
    public MedicalCardInfo getMedicalCardInfo() {
        return medicalCardInfo;
    }
}
