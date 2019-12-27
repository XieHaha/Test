package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.ChargeMemberRecordController;
import com.keydom.ih_patient.activity.member.view.ChargeMemberRecordView;

import org.jetbrains.annotations.Nullable;

public class ChargeMemberRecordActivity extends BaseControllerActivity<ChargeMemberRecordController> implements ChargeMemberRecordView {

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ChargeMemberRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_charge_member_record;
    }

    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("充值记录");
    }
}
