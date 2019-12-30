package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.ChargeMemberController;
import com.keydom.ih_patient.activity.member.view.ChargeMemberView;

import org.jetbrains.annotations.Nullable;

public class ChargeMemberActivity extends BaseControllerActivity<ChargeMemberController> implements ChargeMemberView {

    RecyclerView mPricesRv;

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

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("仁医金卡续约");

        mPricesRv = findViewById(R.id.charge_member_rv);
    }
}
