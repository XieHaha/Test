package com.keydom.ih_patient.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.utils.MD5;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.ClearEditText;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.setting.controller.GestureUnlockController;
import com.keydom.ih_patient.activity.setting.view.GestureUnlockView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.view.UnlockView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

public class GestureUnlockActivity extends BaseControllerActivity<GestureUnlockController> implements GestureUnlockView {
    public static void start(Context context, String type, String gesturePassword, String recordType) {
        Intent intent = new Intent(context, GestureUnlockActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("gesturePassword", gesturePassword);
        intent.putExtra("recordType", recordType);
        context.startActivity(intent);
    }

    public static final String CREATETYPE = "create_type";
    public static final String VALIDATETYPE = "validate_type";
    private String type = "";
    private String recordType = "";
    private String gesturePassword = "";
    private TextView title_label_tv, reset_tv;
    private UnlockView my_unlock_view;
    private LinearLayout regist_step_1_layout, gesture_layout;
    private ClearEditText phoneNumCedt;
    private MButton getCodeBt;
    private EditText messageCodeEdt;
    private Button nextBtn;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_gesture_unlock_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        gesturePassword = getIntent().getStringExtra("gesturePassword");
        recordType = getIntent().getStringExtra("recordType");
        regist_step_1_layout = findViewById(R.id.regist_step_1_layout);
        phoneNumCedt = findViewById(R.id.phone_num_cedt);
        phoneNumCedt.setEnabled(false);
        phoneNumCedt.setText(App.userInfo.getPhoneNumber());
        messageCodeEdt = findViewById(R.id.message_code_cedt);
        getCodeBt = findViewById(R.id.get_code_bt);
        getCodeBt.setOnClickListener(getController());
        nextBtn = findViewById(R.id.register_next_btn);
        nextBtn.setOnClickListener(getController());

        gesture_layout = findViewById(R.id.gesture_layout);
        title_label_tv = findViewById(R.id.title_label_tv);
        reset_tv = findViewById(R.id.reset_tv);
        reset_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_label_tv.setTextColor(Color.parseColor("#666666"));
                title_label_tv.setText("为了您的隐私安全，请设置您的手势密码");
                my_unlock_view.setMode(UnlockView.CREATE_MODE);
                gesturePassword = "";
            }
        });
        my_unlock_view = findViewById(R.id.my_unlock_view);
        if (type.equals(CREATETYPE)) {
            setTitle("设置手势密码");
            title_label_tv.setText("为了您的隐私安全，请设置您的手势密码");
            regist_step_1_layout.setVisibility(View.VISIBLE);
            gesture_layout.setVisibility(View.GONE);
            my_unlock_view.setMode(UnlockView.CREATE_MODE);
        } else {
            setTitle("验证手势密码");
            title_label_tv.setText("请输入手势密码");
            regist_step_1_layout.setVisibility(View.GONE);
            gesture_layout.setVisibility(View.VISIBLE);
            my_unlock_view.setMode(UnlockView.CHECK_MODE);
            reset_tv.setVisibility(View.GONE);
        }

        my_unlock_view.setGestureListener(new UnlockView.CreateGestureListener() {
            @Override
            public void onGestureCreated(String result) {
                gesturePassword = MD5.getStringMD5(result);
                my_unlock_view.setMode(UnlockView.CHECK_MODE);
                title_label_tv.setText("请再次输入手势密码");
            }
        });
        my_unlock_view.setOnUnlockListener(new UnlockView.OnUnlockListener() {
            @Override
            public boolean isUnlockSuccess(String result) {
                if (MD5.getStringMD5(result).equals(gesturePassword))
                    return true;
                else
                    return false;
            }

            @Override
            public void onSuccess() {
                if (type.equals(CREATETYPE)) {
                    getController().setPassword(gesturePassword);
                } else {
                    Global.setLockCount(0);
                    SharePreferenceManager.setLockTime(0);
                    Global.setCurrentTimeMillis(System.currentTimeMillis());
                    if (!"".equals(recordType))
                        EventBus.getDefault().post(new Event(EventType.TURNTORECORD, recordType));
                    else
                        ToastUtil.showMessage(getContext(),"重置成功");
                    finish();
                }
            }

            @Override
            public void onFailure() {
                if (type.equals(CREATETYPE)) {
                    title_label_tv.setText("两次密码不一致");
                    title_label_tv.setTextColor(Color.parseColor("#FF3939"));
                } else {
                    title_label_tv.setText("手势密码输入错误");
                    title_label_tv.setTextColor(Color.parseColor("#FF3939"));
                    if (Global.getLockCount() < 4) {
                        Global.setLockCount(Global.getLockCount() + 1);
                        ToastUtil.showMessage(getContext(), "密码输入错误，你还可以尝试" + (5 - Global.getLockCount()) + "次");
                    } else {
                        SharePreferenceManager.setLockTime(System.currentTimeMillis());
//                        Global.setLockTimeMillis(System.currentTimeMillis());
                        ToastUtil.showMessage(getContext(), "已锁定，请于五分钟后再尝试");
                        finish();
                    }

                }

            }
        });
    }

    @Override
    public void msgInspectSuccess() {
        regist_step_1_layout.setVisibility(View.GONE);
        gesture_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void msgInspectFailed(String msg) {
        ToastUtil.showMessage(this, msg);

    }

    @Override
    public void getMsgCodeSuccess() {
        ToastUtil.showMessage(this, "验证码已发送，请注意查看");
        getCodeBt.startTimer();
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public String getPhoneNum() {
        return phoneNumCedt.getText().toString().trim();
    }

    @Override
    public String getMsgCode() {
        return messageCodeEdt.getText().toString().trim();
    }

    @Override
    public void setPasswordSuccess() {
        EventBus.getDefault().post(new Event(EventType.TURNTORECORD, recordType));
        finish();
    }
}
