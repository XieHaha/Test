package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthContactController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthContactView;

import org.jetbrains.annotations.Nullable;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 紧急联系人
 */
public class HealthContactActivity extends BaseControllerActivity<HealthContactController> implements HealthContactView {

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthContactActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_contact;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_add_relatives);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                finish();
            }
        });
    }
}
