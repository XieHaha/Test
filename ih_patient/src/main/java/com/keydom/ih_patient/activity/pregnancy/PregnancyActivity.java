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
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.PregnancyRecordItem;
import com.keydom.ih_patient.constant.Const;
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

    String mCardNumber;

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo medicalCardInfo) {
        Intent intent = new Intent(context, PregnancyActivity.class);
        intent.putExtra(Const.MEDICAL_CARD_INFO,medicalCardInfo);
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
        getTitleLayout().setWhiteBar();
        getTitleLayout().setBackgroundColor(getResources().getColor(R.color.vip_pregnancy_tool_bar_bg));
        setTitle("产检预约");

        MedicalCardInfo mMedicalCardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.MEDICAL_CARD_INFO);
        //mCardNumber = mMedicalCardInfo.getEleCardNumber();
        mCardNumber = "37e0fcd8-c38f-43e4-b";

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

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.LOAD_MORE));
        mRefreshLayout.autoRefresh();


        findViewById(R.id.pregnancy_order_root_Ll).setOnClickListener(getController());
    }


    @Override
    public void listPersonInspectionRecordSuccess(List<PregnancyRecordItem> list, TypeEnum typeEnum) {
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
