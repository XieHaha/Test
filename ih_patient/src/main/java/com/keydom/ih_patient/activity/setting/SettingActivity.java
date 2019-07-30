package com.keydom.ih_patient.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.setting.controller.SettingContrller;
import com.keydom.ih_patient.activity.setting.view.SettingView;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.Nullable;

/**
 * 设置页面
 */
public class SettingActivity extends BaseControllerActivity<SettingContrller> implements SettingView {
    /**
     * 启动
     */
    public static void start(Context context){
        context.startActivity(new Intent(context,SettingActivity.class));
    }
    private TextView jump_to_change_password_tv,jump_to_user_handbook_tv,logout_tv,gesture_unlock_tv;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Logger.e(this+""+android.os.Process.myPid());
        setTitle("设置");
        jump_to_change_password_tv=this.findViewById(R.id.jump_to_change_password_tv);
        jump_to_change_password_tv.setOnClickListener(getController());
        jump_to_user_handbook_tv=this.findViewById(R.id.jump_to_user_handbook_tv);
        logout_tv=this.findViewById(R.id.logout_tv);
        logout_tv.setOnClickListener(getController());
        gesture_unlock_tv=this.findViewById(R.id.gesture_unlock_tv);
        gesture_unlock_tv.setOnClickListener(getController());
    }

    @Override
    public void finishSetting() {
        finish();
    }

    @Override
    public void finish() {
        Logger.e("finish"+android.os.Process.myPid());
        super.finish();
    }
}
