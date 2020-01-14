package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyView;
import com.keydom.ih_patient.adapter.PregnancyRecordAdapter;
import com.keydom.ih_patient.bean.RenewalRecordItem;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PregnancyActivity extends BaseControllerActivity<PregnancyController> implements PregnancyView {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mLastDaysTv;
    private TextView mBabyHeightTv;
    private TextView mBabyWeightTv;
    private TextView mBabyMeettingDaysTv;
    private TextView mLastWeeksTv;
    private TextView mOrderCheckProjectsTv;
    private TextView mOrderCountsTimesTv;
    private TextView mOrderDatesTv;
    private TextView mOrderNowTv;
    private TextView mOrderCheckTv;
    private TextView mOrderDoctorTv;
    private TextView mFinishOrderCountsTv;


    private PregnancyRecordAdapter mAdapter;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, PregnancyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        setTitle("产检预约");

        mRecyclerView = findViewById(R.id.pregnancy_records_rv);
        mRefreshLayout = findViewById(R.id.pregnancy_refresh);
        mLastDaysTv = findViewById(R.id.pregnancy_last_days_tv);
        mBabyHeightTv = findViewById(R.id.pregnancy_baby_height_tv);
        mBabyWeightTv = findViewById(R.id.pregnancy_baby_weight_tv);
        mBabyMeettingDaysTv = findViewById(R.id.pregnancy_baby_meetting_days_tv);
        mLastWeeksTv = findViewById(R.id.pregnancy_last_weeks_tv);
        mOrderCheckProjectsTv = findViewById(R.id.pregnancy_order_check_projects_tv);
        mOrderCountsTimesTv = findViewById(R.id.pregnancy_order_counts_times_tv);
        mOrderDatesTv = findViewById(R.id.pregnancy_order_dates_tv);
        mOrderNowTv = findViewById(R.id.pregnancy_order_now_tv);
        mOrderCheckTv = findViewById(R.id.pregnancy_order_check_tv);
        mOrderDoctorTv = findViewById(R.id.pregnancy_order_doctor_tv);
        mFinishOrderCountsTv = findViewById(R.id.pregnancy_finish_order_counts_tv);

        mAdapter = new PregnancyRecordAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().getRenewalRecord(mRefreshLayout, 1, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getRenewalRecord(mRefreshLayout, 1, TypeEnum.LOAD_MORE));
        mRefreshLayout.autoRefresh();
    }


    @Override
    public void paymentListSuccess(List<RenewalRecordItem> list, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (typeEnum == TypeEnum.REFRESH) {
            mAdapter.replaceData(list);
        } else {
            mAdapter.addData(list);
        }
        getController().currentPagePlus();
    }
}
