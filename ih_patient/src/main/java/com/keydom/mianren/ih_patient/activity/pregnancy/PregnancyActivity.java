package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyView;
import com.keydom.mianren.ih_patient.adapter.PregnancyRecordAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PregnancyDetailBean;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;
import com.keydom.mianren.ih_patient.bean.event.PregnancyOrderSuccess;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 顿顿
 */
public class PregnancyActivity extends BaseControllerActivity<PregnancyController> implements PregnancyView {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mLastDaysTv;
    private TextView mBabyHeightTv;
    private TextView mBabyWeightTv;
    private TextView mBabyMeettingDaysTv;
    private TextView mLastWeeksTv;
    private TextView mOrderCheckProjectsTv;
    private TextView mFinishOrderCountsTv;
    private ImageView mIsOrderIv;
    private LinearLayout mOrderRootLl, layoutOutpatientReserve, layoutCheckReserve;

    private TextView checkDateTv, checkTimeTv, pregnancyDateTv, pregnancyTimeTv;


    private PregnancyRecordAdapter mAdapter;

    String mCardNumber;

    private PregnancyDetailBean mPregnancyDetailBean;
    private PregnancyRecordItem pregnancyRecordItem;
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

        MedicalCardInfo mMedicalCardInfo =
                (MedicalCardInfo) getIntent().getSerializableExtra(Const.MEDICAL_CARD_INFO);
        mCardNumber = mMedicalCardInfo.getEleCardNumber();

        mRecyclerView = findViewById(R.id.pregnancy_records_rv);
        mRefreshLayout = findViewById(R.id.pregnancy_refresh);
        mLastDaysTv = findViewById(R.id.pregnancy_last_days_tv);
        mBabyHeightTv = findViewById(R.id.pregnancy_baby_height_tv);
        mBabyWeightTv = findViewById(R.id.pregnancy_baby_weight_tv);
        mBabyMeettingDaysTv = findViewById(R.id.pregnancy_baby_meetting_days_tv);
        mLastWeeksTv = findViewById(R.id.pregnancy_last_weeks_tv);
        mOrderCheckProjectsTv = findViewById(R.id.pregnancy_order_check_projects_tv);
        mIsOrderIv = findViewById(R.id.pregnancy_is_order_iv);
        mFinishOrderCountsTv = findViewById(R.id.pregnancy_finish_order_counts_tv);
        mOrderRootLl = findViewById(R.id.pregnancy_order_root_Ll);

        checkDateTv = findViewById(R.id.item_check_date_tv);
        checkTimeTv = findViewById(R.id.item_check_time_tv);
        pregnancyDateTv = findViewById(R.id.item_pregnancy_record_date_tv);
        pregnancyTimeTv = findViewById(R.id.item_pregnancy_record_time_tv);
        layoutOutpatientReserve = findViewById(R.id.layout_outpatient_reserve);
        layoutCheckReserve = findViewById(R.id.layout_check_reserve);

        layoutOutpatientReserve.setOnClickListener(getController());
        layoutCheckReserve.setOnClickListener(getController());

        mAdapter = new PregnancyRecordAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        mRefreshLayout.setOnRefreshListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.REFRESH));
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.LOAD_MORE));

        pageLoading();
        getController().listPersonInspectionRecord(mRefreshLayout, mCardNumber, TypeEnum.REFRESH);
        getController().getPregnancyDetail(mCardNumber);

        setReloadListener((v, status) -> getController().getPregnancyDetail(mCardNumber));

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
    public void listPersonInspectionRecordSuccess(List<PregnancyRecordItem> list,
                                                  TypeEnum typeEnum) {
        mOrderRootLl.setVisibility(View.GONE);
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();
        if (list.size() >= Const.PAGE_SIZE) {
            mRefreshLayout.finishLoadMore();
        } else {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
        }

        for (int i = 0; i < list.size(); i++) {
            PregnancyRecordItem data = list.get(i);
            if (!data.isFinsh()) {
                pregnancyRecordItem = data;
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

    @Override
    public void listPersonInspectionRecordFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getPregnancyDetailSuccess(PregnancyDetailBean data) {
        pageLoadingSuccess();
        if (null != data) {
            mPregnancyDetailBean = data;
            mBabyHeightTv.setText(data.getBabyLength() + "mm");
            mBabyWeightTv.setText(data.getBabyWeight() + "g");
            mLastDaysTv.setText(data.getShowDate());
            mBabyMeettingDaysTv.setText(data.getPregnantDay());
        } else {
            pageEmpty();
        }
    }

    @Override
    public void getPregnancyDetailFailed(int code, String msg) {
        if (code == 300) {
            ToastUtil.showMessage(this, msg);
            finish();
        }
        pageLoadingFail();
    }

    @Override
    public PregnancyDetailBean getPregnancyDetail() {
        return mPregnancyDetailBean;
    }

    @Override
    public String getRecordID() {
        return mRecordId;
    }

    @Override
    public PregnancyRecordItem getPregnancyRecordItem() {
        return pregnancyRecordItem;
    }

    public void orderNext(PregnancyRecordItem data) {
        if (null != data) {
            mOrderRootLl.setVisibility(View.VISIBLE);
            mLastWeeksTv.setText(data.getPreWeek());
            mOrderCheckProjectsTv.setText(data.getProjectName());
            checkDateTv.setText(data.getPrenatalDate());
            checkTimeTv.setText(data.getPrenatalTimeInterval());
            pregnancyDateTv.setText(data.getAppointDate());
            pregnancyTimeTv.setText(data.getAppointTimeInterval());

            if (data.isAppointed()) {
                mOrderRootLl.setOnClickListener(getController());
                mIsOrderIv.setSelected(true);
            } else {
                mIsOrderIv.setSelected(false);
            }
        }
    }
}
