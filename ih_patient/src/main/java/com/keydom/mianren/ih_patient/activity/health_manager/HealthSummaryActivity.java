package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthSummaryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryView;
import com.keydom.mianren.ih_patient.adapter.HealthSummaryAdapter;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康总结
 */
public class HealthSummaryActivity extends BaseControllerActivity<HealthSummaryController> implements HealthSummaryView {
    @BindView(R.id.health_summary_select_time_tv)
    TextView healthSummarySelectTimeTv;
    @BindView(R.id.health_summary_recycler_view)
    RecyclerView healthSummaryRecyclerView;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    //    @BindView(R.id.health_summary_refresh_layout)
    //    SmartRefreshLayout healthSummaryRefreshLayout;


    private String patientId;
    private String curSelectDate;
    private HealthSummaryAdapter healthSummaryAdapter;

    private ArrayList<HealthSummaryBean> healthSummaryBeans = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context, String patientId) {
        Intent intent = new Intent(context, HealthSummaryActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_summary;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_health_summary);

        patientId = getIntent().getStringExtra(Const.PATIENT_ID);

        healthSummarySelectTimeTv.setOnClickListener(getController());

        healthSummaryAdapter = new HealthSummaryAdapter(healthSummaryBeans);
        healthSummaryAdapter.setOnItemClickListener(getController());
        healthSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        healthSummaryRecyclerView.setAdapter(healthSummaryAdapter);
        //        healthSummaryRefreshLayout.setOnRefreshListener(refreshLayout -> {
        //            getController().patientHealthConclusionList();
        //        });
        getController().patientHealthConclusionList();
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("time", curSelectDate);
        params.put("currentPage", 0);
        params.put("pageSize", 99);
        params.put("patientId", patientId);
        return params;
    }

    @Override
    public ArrayList<HealthSummaryBean> getHealthSummaryBeans() {
        return healthSummaryBeans;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public void onSelectDate(Date date) {
        curSelectDate = DateUtils.dateToString(date, DateUtils.YYYY);
        healthSummarySelectTimeTv.setText(curSelectDate + "年");
        getController().patientHealthConclusionList();
    }

    @Override
    public void requestHealthSummaryListSuccess(List<HealthSummaryBean> data) {
        healthSummaryBeans.clear();
        healthSummaryBeans.addAll(data);
        healthSummaryAdapter.setNewData(healthSummaryBeans);
        if (healthSummaryBeans.size() > 0) {
            healthSummaryRecyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            healthSummaryRecyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestHealthSummaryListFailed(String error) {
        ToastUtil.showMessage(this, error);
        healthSummaryRecyclerView.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }
}
