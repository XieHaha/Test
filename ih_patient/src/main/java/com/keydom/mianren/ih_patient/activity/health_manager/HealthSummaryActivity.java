package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthSummaryController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSummaryView;
import com.keydom.mianren.ih_patient.adapter.HealthSummaryAdapter;
import com.keydom.mianren.ih_patient.bean.HealthSummaryBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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
    @BindView(R.id.health_summary_refresh_layout)
    SmartRefreshLayout healthSummaryRefreshLayout;

    private HealthSummaryAdapter healthSummaryAdapter;

    private ArrayList<HealthSummaryBean> healthSummaryBeans = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthSummaryActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_summary;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_health_summary);

        healthSummaryBeans.add(new HealthSummaryBean());
        healthSummaryBeans.add(new HealthSummaryBean());
        healthSummaryBeans.add(new HealthSummaryBean());
        healthSummaryBeans.add(new HealthSummaryBean());
        healthSummaryAdapter = new HealthSummaryAdapter(healthSummaryBeans);
        healthSummaryAdapter.setOnItemClickListener(getController());
        healthSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        healthSummaryRecyclerView.setAdapter(healthSummaryAdapter);
        healthSummaryRefreshLayout.setOnRefreshListener(refreshLayout -> {
        });
    }
}
