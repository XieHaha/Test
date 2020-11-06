package com.keydom.mianren.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.personal.controller.SettingController;
import com.keydom.mianren.ih_doctor.activity.personal.view.SettingView;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：设置页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class SettingActivity extends BaseControllerActivity<SettingController> implements SettingView {


    private TextView updatePassword, userBook, exit,versionName;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("设置");
        updatePassword = this.findViewById(R.id.update_password);
        userBook = this.findViewById(R.id.user_book);
        exit = this.findViewById(R.id.exit);
        updatePassword.setOnClickListener(getController());
        userBook.setOnClickListener(getController());
        exit.setOnClickListener(getController());

        versionName = this.findViewById(R.id.version_name);
        versionName.setText("v" + CommonUtils.getAppVersionName(this));
    }
}
