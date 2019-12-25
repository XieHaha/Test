package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.SignMemberController;
import com.keydom.ih_patient.activity.member.view.SignMemberView;

import org.jetbrains.annotations.Nullable;

public class SignMemberActivity extends BaseControllerActivity<SignMemberController> implements SignMemberView {

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, SignMemberActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sign_member;
    }


    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("仁医金卡签约");
    }
}
