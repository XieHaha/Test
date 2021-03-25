package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.InterventionPlanController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanView;
import com.keydom.mianren.ih_patient.adapter.InterventionPlanAdapter;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 干预方案
 */
public class InterventionPlanActivity extends BaseControllerActivity<InterventionPlanController> implements InterventionPlanView {
    @BindView(R.id.layout_title)
    RelativeLayout layoutBg;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.intervention_plan_refresh_layout)
    SmartRefreshLayout swipeRefreshLayout;
    @BindView(R.id.intervention_plan_recycler_view)
    RecyclerView recyclerView;

    private InterventionPlanAdapter interventionPlanAdapter;

    private ArrayList<InterventionPlanBean> interventionPlanBeans = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, InterventionPlanActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_intervention_plan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        statusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                StatusBarUtils.getStateBarHeight(this)));
        StatusBarUtils.setStatusBarTranslucent(this);
        tvTitle.setText(R.string.txt_intervention_plan);
        layoutBg.setAlpha(0);
        statusBar.setAlpha(0);
        StatusBarUtils.setStatusBarColor(this, false);

        ivBack.setOnClickListener(getController());

        interventionPlanBeans.add(new InterventionPlanBean());
        interventionPlanBeans.add(new InterventionPlanBean());
        interventionPlanBeans.add(new InterventionPlanBean());
        interventionPlanBeans.add(new InterventionPlanBean());
        interventionPlanAdapter = new InterventionPlanAdapter(interventionPlanBeans);
        interventionPlanAdapter.setOnItemChildClickListener(getController());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interventionPlanAdapter);
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
        });
    }

    @Override
    public void finishPage() {
        finish();
    }
}
