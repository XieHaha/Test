package com.keydom.mianren.ih_patient.activity.nursing_order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_order.controller.ChargeBackOrderController;
import com.keydom.mianren.ih_patient.activity.nursing_order.view.ChargeBackOrderView;
import com.keydom.mianren.ih_patient.adapter.NursingChargeBackAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.NursingOrderChargeBackBean;
import com.keydom.mianren.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2018/12/18 on 17:56
 * des:退单详情页面
 */
public class ChargeBackOrderActivity extends BaseControllerActivity<ChargeBackOrderController> implements ChargeBackOrderView {
    //订单号
    public static final String ORDERNUM = "orderNum";
    //订单状态
    public static final String STATUS = "status";

    private View mHead;
    private RecyclerView mRecyclerView;
    private NursingChargeBackAdapter mAdapter;
    private TextView mConfirm;
    private LinearLayout mBGroup;
    /**
     * 订单号
     */
    private String mOrderNuml;
    /**
     * 订单状态
     */
    private int mStatus;
    private TextView mRules;
    /**
     * 退单详情
     */
    private NursingOrderChargeBackBean mBean;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_charge_back_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("退单");
        getView();
        mStatus = getIntent().getIntExtra(STATUS, 0);
        mOrderNuml = getIntent().getStringExtra(ORDERNUM);
        getController().getChargeBackData(mOrderNuml);

    }

    /**
     * 查找控件
     */
    private void getView() {
        mAdapter = new NursingChargeBackAdapter(new ArrayList<>());
        mHead = LayoutInflater.from(this).inflate(R.layout.nursing_order_charge_back_head, null);
        mRules = mHead.findViewById(R.id.rules);
        mAdapter.addHeaderView(mHead);
        mRecyclerView = findViewById(R.id.rv);
        mBGroup = findViewById(R.id.bottom_group);
        findViewById(R.id.go_back).setOnClickListener(getController());
        mConfirm = findViewById(R.id.confirm);
        mConfirm.setOnClickListener(getController());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void getData(List<MultiItemEntity> items) {
        mAdapter.setNewData(items);
    }

    @Override
    public NursingOrderChargeBackBean getChargeBackBean() {
        if (mBean == null) {
            return null;
        }
        mBean.setOrderNum(mOrderNuml);
        mBean.setStatus(mStatus);
        return mBean;
    }

    @Override
    public void getInitBean(NursingOrderChargeBackBean bean) {
        if (bean == null) {
            return;
        }
        mRules.setText(bean.getChargeRules());
        if (mStatus == NursingOrderDetailBean.STATE5 || mStatus == NursingOrderDetailBean.STATE6) {
            mConfirm.setText("确认退单");
        }else{
            mConfirm.setText("确认退款（¥" + bean.getTotalReturnMoney() + "元）");
            if (bean.getTotalReturnMoney().equals("0.00")){
                mBGroup.setVisibility(View.GONE);
            }else{
                mBGroup.setVisibility(View.VISIBLE);
            }
        }
        bean.setStatus(mStatus);
        mBean = bean;
    }

    @Override
    public void chargeBackSuccess() {
        EventBus.getDefault().post(new Event(EventType.CHARGEBACKSUCCESS, null));
        finish();
        ActivityUtils.finishActivity(WaitForAdmissionActivity.class);
    }

}
