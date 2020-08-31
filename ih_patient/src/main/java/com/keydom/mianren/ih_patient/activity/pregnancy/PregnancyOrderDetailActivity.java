package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyOrderDetailController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyOrderDetailView;
import com.keydom.mianren.ih_patient.adapter.PregnancyOrderDetailAdapter;
import com.keydom.mianren.ih_patient.bean.PregnancyDetailBean;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderBean;
import com.keydom.mianren.ih_patient.bean.PregnancyOrderDetailItem;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordItem;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * @author 顿顿
 */
public class PregnancyOrderDetailActivity extends BaseControllerActivity<PregnancyOrderDetailController> implements PregnancyOrderDetailView {


    private PregnancyOrderDetailAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mWeeksTv;
    private TextView mDescTv;
    private TextView projectNameTv, projectTimeTv;

    private PregnancyDetailBean mPregnancyDetailBean;

    private PregnancyRecordItem pregnancyRecordItem;
    private String mRecordId;


    /**
     * 启动
     */
    public static void start(Context context, PregnancyDetailBean pregnancyDetailBean,
                             PregnancyRecordItem item) {
        Intent intent = new Intent(context, PregnancyOrderDetailActivity.class);
        intent.putExtra(Const.PREGNANCY_DETAIL, pregnancyDetailBean);
        intent.putExtra("item", item);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_order_detail;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        getTitleLayout().setWhiteBar();
        getTitleLayout().setBackgroundColor(getResources().getColor(R.color.vip_pregnancy_detail_tool_bar_bg));
        setTitle("产检预约");

        mPregnancyDetailBean =
                (PregnancyDetailBean) getIntent().getSerializableExtra(Const.PREGNANCY_DETAIL);
        pregnancyRecordItem = (PregnancyRecordItem) getIntent().getSerializableExtra("item");

        mWeeksTv = findViewById(R.id.pregnancy_order_detail_weeks_tv);
        mDescTv = findViewById(R.id.pregnancy_order_detail_desc_tv);
        mRecyclerView = findViewById(R.id.pregnancy_order_detail_rv);
        projectNameTv = findViewById(R.id.project_name_tv);
        projectTimeTv = findViewById(R.id.project_time_tv);
        mAdapter = new PregnancyOrderDetailAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        if (null != mPregnancyDetailBean) {
            mWeeksTv.setText(mPregnancyDetailBean.getShowDate());
            mDescTv.setText(mPregnancyDetailBean.getContext());
        }

        if (pregnancyRecordItem != null) {
            projectNameTv.setText(pregnancyRecordItem.getProjectName());
            projectTimeTv.setText(pregnancyRecordItem.getPreWeek());
            mRecordId = pregnancyRecordItem.getRecordId();
        }

        pageLoading();
        getController().getPregnancyOrderDetails(mRecordId);
    }


    @Override
    public void getDetailProductInspectionSuccess(PregnancyOrderBean data) {
        if (null != data && null != data.getData()) {
            pageLoadingSuccess();
            for (int i = 0; i < data.getData().size(); i++) {
                PregnancyOrderDetailItem item = data.getData().get(i);
                if (!TextUtils.isEmpty(item.getTimeInterval())) {
                    mAdapter.addData(item);
                    if (item.getAppointType() == 2) {
                        getController().getAntDoctor(item.getPrenatalDate(), item.getTimeInterval());
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        } else {
            pageEmpty();
        }
    }

    @Override
    public void requestDoctorSuccess(String name) {
        mAdapter.setDoctorName(name);
    }

    @Override
    public void getDetailProductInspectionFailed(String msg) {
        ToastUtils.showShort(msg);
        pageLoadingFail();
    }
}
