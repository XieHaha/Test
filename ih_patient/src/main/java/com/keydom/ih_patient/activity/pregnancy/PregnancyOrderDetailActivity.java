package com.keydom.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.controller.PregnancyOrderDetailController;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyOrderDetailView;
import com.keydom.ih_patient.adapter.PregnancyOrderDetailAdapter;
import com.keydom.ih_patient.bean.PregnancyDetailBean;
import com.keydom.ih_patient.bean.PregnancyOrderBean;
import com.keydom.ih_patient.bean.PregnancyOrderDetailItem;
import com.keydom.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PregnancyOrderDetailActivity extends BaseControllerActivity<PregnancyOrderDetailController> implements PregnancyOrderDetailView {


    private PregnancyOrderDetailAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mWeeksTv;
    private TextView mDescTv;

    private PregnancyDetailBean mPregnancyDetailBean;
    private String mRecordId;


    /**
     * 启动
     */
    public static void start(Context context, PregnancyDetailBean pregnancyDetailBean, String recordId) {
        Intent intent = new Intent(context, PregnancyOrderDetailActivity.class);
        intent.putExtra(Const.PREGNANCY_DETAIL, pregnancyDetailBean);
        intent.putExtra(Const.RECORD_ID, recordId);
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

        mPregnancyDetailBean = (PregnancyDetailBean) getIntent().getSerializableExtra(Const.PREGNANCY_DETAIL);
        mRecordId = (String) getIntent().getSerializableExtra(Const.RECORD_ID);

        mWeeksTv = findViewById(R.id.pregnancy_order_detail_weeks_tv);
        mDescTv = findViewById(R.id.pregnancy_order_detail_desc_tv);
        mRecyclerView = findViewById(R.id.pregnancy_order_detail_rv);
        mAdapter = new PregnancyOrderDetailAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);


        if (null != mPregnancyDetailBean) {
            mWeeksTv.setText(mPregnancyDetailBean.getShowDate());
            mDescTv.setText(mPregnancyDetailBean.getContext());
        }


        getController().getPregnancyOrderDetails(mRecordId);
    }


    @Override
    public void getDetailProductInspectionSuccess(PregnancyOrderBean data) {

        if (null != data && null != data.getData()) {
            for (int i = 0; i < data.getData().size(); i++) {
                PregnancyOrderDetailItem item = data.getData().get(i);

                if(!TextUtils.isEmpty(item.getTimeInterval())){
                    mAdapter.addData(item);
                }
            }

            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getDetailProductInspectionFailed(String msg) {
        ToastUtils.showShort(msg);
    }


}
