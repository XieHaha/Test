package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.ChargeMemberController;
import com.keydom.ih_patient.activity.member.view.ChargeMemberView;
import com.keydom.ih_patient.adapter.ChargeMemberAdapter;
import com.keydom.ih_patient.bean.ChargeMemberPriceItemBean;
import com.keydom.ih_patient.bean.VIPDetailBean;
import com.keydom.ih_patient.bean.event.ChargeMemberSuccess;
import com.keydom.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChargeMemberActivity extends BaseControllerActivity<ChargeMemberController> implements ChargeMemberView {

    TextView mBalanceTv;
    TextView mEndDateTv;
    RecyclerView mPricesRv;
    ChargeMemberAdapter mAdapter;
    List<ChargeMemberPriceItemBean> mDatas;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ChargeMemberActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_charge_member;
    }

    @Override
    public void beforeInit() {
        mDatas = new ArrayList<ChargeMemberPriceItemBean>();
        mDatas.add(new ChargeMemberPriceItemBean(1000, false, false));
        mDatas.add(new ChargeMemberPriceItemBean(2000, false, false));
        mDatas.add(new ChargeMemberPriceItemBean(3000, false, false));
        mDatas.add(new ChargeMemberPriceItemBean(4000, false, false));
        mDatas.add(new ChargeMemberPriceItemBean(5000, false, false));
        mDatas.add(new ChargeMemberPriceItemBean(0, false, true));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        setTitle("仁医金卡续约");

        mBalanceTv = findViewById(R.id.charge_member_balance_tv);
        mEndDateTv = findViewById(R.id.charge_member_end_date_tv);
        mPricesRv = findViewById(R.id.charge_member_rv);
        findViewById(R.id.charge_member_commit_charge_tv).setOnClickListener(getController());

        mAdapter = new ChargeMemberAdapter(mDatas);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
        mPricesRv.setLayoutManager(mGridLayoutManager);
        mPricesRv.setAdapter(mAdapter);


        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mPricesRv.setHasFixedSize(true);
        mPricesRv.setNestedScrollingEnabled(false);

        getController().getMyVipCard();
        pageLoading();
    }

    @Override
    public void paySuccess() {
        ToastUtils.showShort("续约成功");
        EventBus.getDefault().post(new ChargeMemberSuccess());
        getController().getMyVipCard();
    }

    @Override
    public double getSelectedPrice() {
        ChargeMemberPriceItemBean lastItem = mDatas.get(mDatas.size() - 1);

        if (lastItem.getPrice() > 0) return lastItem.getPrice();

        for (ChargeMemberPriceItemBean item : mDatas) {
            if (item.isSelected()) {
                return item.getPrice();
            }
        }
        return 0;
    }

    @Override
    public void getMyVipCardSuccess(VIPDetailBean data) {
        if (null != data) {
            pageLoadingSuccess();
            if (null != data.getEndTime()) {
                mEndDateTv.setText(DateUtils.getYMDfromYMDHMS(data.getEndTime()));
            }
            mBalanceTv.setText(data.getSurplusAmount() + "元");
        }else{
            pageEmpty();
        }
    }

    @Override
    public void getMyVipCardFail(String msg) {
        pageLoadingFail();
    }
}
