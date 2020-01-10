package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.ChargeMemberController;
import com.keydom.ih_patient.activity.member.view.ChargeMemberView;
import com.keydom.ih_patient.adapter.ChargeMemberAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChargeMemberActivity extends BaseControllerActivity<ChargeMemberController> implements ChargeMemberView {

    RecyclerView mPricesRv;
    ChargeMemberAdapter mAdapter;
    List<String> mDatas;

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
        mDatas = new ArrayList<String>();
        mDatas.add("1000");
        mDatas.add("2000");
        mDatas.add("3000");
        mDatas.add("4000");
        mDatas.add("5000");
        mDatas.add("0");
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("仁医金卡续约");

        mPricesRv = findViewById(R.id.charge_member_rv);
        findViewById(R.id.charge_member_commit_charge_tv).setOnClickListener(getController());

        mAdapter = new ChargeMemberAdapter(mDatas);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(),3);
        mPricesRv.setLayoutManager(mGridLayoutManager);
        mPricesRv.setAdapter(mAdapter);


        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        mPricesRv.setHasFixedSize(true);
        mPricesRv.setNestedScrollingEnabled(false);

        getController().init();

    }

    @Override
    public void paySuccess() {

    }
}
