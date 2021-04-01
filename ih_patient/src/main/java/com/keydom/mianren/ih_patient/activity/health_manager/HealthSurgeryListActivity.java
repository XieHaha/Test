package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthSurgeryListController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthSurgeryListView;
import com.keydom.mianren.ih_patient.adapter.SurgeryHistoryAdapter;
import com.keydom.mianren.ih_patient.bean.PatientSurgeryHistoryBean;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 手术史
 */
public class HealthSurgeryListActivity extends BaseControllerActivity<HealthSurgeryListController> implements HealthSurgeryListView {
    @BindView(R.id.health_surgery_list_recycler_view)
    RecyclerView healthSurgeryListRecyclerView;
    //    @BindView(R.id.health_surgery_list_refresh_layout)
    //    SmartRefreshLayout healthSurgeryListRefreshLayout;
    @BindView(R.id.health_surgery_list_none_tv)
    TextView healthSurgeryListNoneTv;

    private SurgeryHistoryAdapter historyAdapter;

    private List<PatientSurgeryHistoryBean> historyBeans;

    /**
     * 启动
     */
    public static void start(Context context, List<PatientSurgeryHistoryBean> list) {
        Intent intent = new Intent(context, HealthSurgeryListActivity.class);
        intent.putExtra(Const.DATA, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_surgery_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_surgery);
        historyBeans =
                (List<PatientSurgeryHistoryBean>) getIntent().getSerializableExtra(Const.DATA);

        historyAdapter = new SurgeryHistoryAdapter(historyBeans);
        healthSurgeryListRecyclerView.setAdapter(historyAdapter);

        if (historyBeans == null || historyBeans.size() == 0) {
            healthSurgeryListNoneTv.setVisibility(View.VISIBLE);
            healthSurgeryListRecyclerView.setVisibility(View.GONE);
        } else {
            healthSurgeryListRecyclerView.setVisibility(View.VISIBLE);
            healthSurgeryListNoneTv.setVisibility(View.GONE);
        }
    }
}
