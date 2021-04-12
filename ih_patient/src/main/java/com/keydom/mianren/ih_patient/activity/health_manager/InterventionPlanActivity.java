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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.InterventionPlanController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.InterventionPlanView;
import com.keydom.mianren.ih_patient.adapter.InterventionPlanAdapter;
import com.keydom.mianren.ih_patient.bean.InterventionPlanBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    private String patientId;

    private InterventionPlanAdapter interventionPlanAdapter;

    private ArrayList<InterventionPlanBean> interventionPlanBeans = new ArrayList<>();

    /**
     * 启动
     */
    public static void start(Context context, String patientId) {
        Intent intent = new Intent(context, InterventionPlanActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
        context.startActivity(intent);
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

        patientId = getIntent().getStringExtra(Const.PATIENT_ID);

        interventionPlanAdapter = new InterventionPlanAdapter(interventionPlanBeans);
        interventionPlanAdapter.setOnItemChildClickListener(getController());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(interventionPlanAdapter);
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getController().interventionPlanList();
        });

        getController().interventionPlanList();
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        //0 不分页，1 分页
        params.put("isPage", 0);
        params.put("currentPage", 0);
        params.put("pageSize", 99);
        params.put("patientId", patientId);
        return params;
    }

    @Override
    public void requestInterventionPlanListSuccess(List<InterventionPlanBean> data) {
        swipeRefreshLayout.finishRefresh();
        interventionPlanBeans.clear();
        interventionPlanBeans.addAll(data);
        interventionPlanAdapter.setNewData(interventionPlanBeans);

        if (interventionPlanBeans.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestInterventionPlanListFailed(String error) {
        ToastUtil.showMessage(this, error);
        recyclerView.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public ArrayList<InterventionPlanBean> getInterventionPlanBeans() {
        return interventionPlanBeans;
    }

    @Override
    public void finishPage() {
        finish();
    }
}
