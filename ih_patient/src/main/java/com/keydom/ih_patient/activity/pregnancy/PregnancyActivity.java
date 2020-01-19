package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyView;
import com.keydom.ih_patient.adapter.PregnancyRecordAdapter;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.PregnancyDetailBean;
import com.keydom.ih_patient.bean.PregnancyRecordItem;
import com.keydom.ih_patient.bean.event.PregnancyOrderSuccess;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    private ImageView mIsOrderIv;


    private PregnancyRecordAdapter mAdapter;

    String mCardNumber;

    private PregnancyDetailBean mPregnancyDetailBean;
    private String mRecordId;

    /**
     * 启动
     */
    public static void start(Context context, MedicalCardInfo medicalCardInfo) {
        Intent intent = new Intent(context, PregnancyActivity.class);
        intent.putExtra(Const.MEDICAL_CARD_INFO, medicalCardInfo);
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
        mIsOrderIv = findViewById(R.id.pregnancy_is_order_iv);
        mFinishOrderCountsTv = findViewById(R.id.pregnancy_finish_order_counts_tv);

        mAdapter = new PregnancyRecordAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.LOAD_MORE));
        mRefreshLayout.autoRefresh();


        findViewById(R.id.pregnancy_order_root_Ll).setOnClickListener(getController());
        mOrderNowTv.setOnClickListener(getController());
        mOrderCheckTv.setOnClickListener(getController());
        mOrderDoctorTv.setOnClickListener(getController());

        getController().getPregnancyDetail(mCardNumber);

        EventBus.getDefault().register(this);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(PregnancyOrderSuccess event) {
        getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.REFRESH);
        getController().getPregnancyDetail(mCardNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void listPersonInspectionRecordSuccess(List<PregnancyRecordItem> list, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();


        if (null != list) {

            for (int i = 0; i < list.size(); i++) {
                PregnancyRecordItem data = list.get(i);
                if (!data.isFinsh()) {
                    mRecordId = data.getRecordId();
                    orderNext(data);
                    list.remove(i);
                    break;
                }
            }



            if (typeEnum == TypeEnum.REFRESH) {
                mFinishOrderCountsTv.setText("已完成" + list.size() + "次产检");
                mAdapter.replaceData(list);
            } else {
                mAdapter.addData(list);
            }

            getController().currentPagePlus();
        }

    }

    @Override
    public void listPersonInspectionRecordFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getPregnancyDetailSuccess(PregnancyDetailBean data) {
        if (null != data) {
            mPregnancyDetailBean = data;
            mBabyHeightTv.setText(data.getBabyLength() + "mm");
            mBabyWeightTv.setText(data.getBabyWeight() + "g");
            mLastDaysTv.setText(data.getShowDate());
            mBabyMeettingDaysTv.setText(data.getPregnantDay());
        }
    }

    @Override
    public void getPregnancyDetailFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public PregnancyDetailBean getPregnancyDetail() {
        return mPregnancyDetailBean;
    }

    @Override
    public String getRecordID() {
        return mRecordId;
    }


    public void orderNext(PregnancyRecordItem data) {
        if (null != data) {
            mLastWeeksTv.setText(data.getPreWeek());
            mOrderCheckProjectsTv.setText(data.getProjectName());
            mOrderCountsTimesTv.setText(data.getTimes());
            mOrderDatesTv.setText(data.getPrenatalDate());

            if (data.isAppointed()) {
                mOrderNowTv.setVisibility(View.GONE);
                mOrderCheckTv.setVisibility(View.VISIBLE);
                mOrderDoctorTv.setVisibility(View.VISIBLE);
                mIsOrderIv.setSelected(true);
            } else {
                mOrderNowTv.setVisibility(View.VISIBLE);
                mOrderCheckTv.setVisibility(View.GONE);
                mOrderDoctorTv.setVisibility(View.GONE);
                mIsOrderIv.setSelected(false);
            }

        }
    }
}
