package com.keydom.mianren.ih_doctor.activity.electronic_signature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.controller.ApplySignatureController;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.ApplySignatureView;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.SignRegInfoBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @link Author: song
 * <p>
 * Create: 19/3/6 下午3:48
 * <p>
 * Changes (from 19/3/6)
 * <p>
 * 19/3/6 : Create InspectionDetailActivity.java (song);
 */
public class ChangeSignatureActivity extends BaseControllerActivity<ApplySignatureController> implements ApplySignatureView {


    private String userCode = "";
    private TextView user_code_tv;
    private EditText phone_input_et;

    /**
     * 启动电子签名设置页面
     *
     * @param context
     */
    public static void start(Context context, String userCode) {
        Intent intent = new Intent(context, ChangeSignatureActivity.class);
        intent.putExtra(Const.DATA, userCode);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_change_sign_layout;
    }

    private void initView() {
        user_code_tv = findViewById(R.id.user_code_tv);
        phone_input_et = findViewById(R.id.phone_input_et);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("激活变更");
        setRightTxt("提交");
        initView();
        userCode = getIntent().getStringExtra(Const.DATA);
        user_code_tv.setText(userCode);
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                String phoneNum = phone_input_et.getText().toString().trim();
                if (PhoneUtils.isMobileEnable(phoneNum)) {
                    getController().changeMobile(phoneNum);
                } else {
                    ToastUtil.showMessage(getContext(), "请输入正确的手机号");
                }
            }
        });
    }

    @Override
    public Map<String, Object> getApplyMap() {
        return null;
    }

    @Override
    public void applySuccess(String result) {

    }

    @Override
    public void applyFailed(String errMsg) {

    }

    @Override
    public void getIdentifyingCodeSuccess() {

    }

    @Override
    public void getIdentifyingCodeFailed(String errMsg) {

    }

    @Override
    public void getUserSuccess(SignRegInfoBean data) {

    }

    @Override
    public void getUserFailed(String errMsg) {

    }

    @Override
    public void changeMobileSuccess(String msg) {
        finish();
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_SIGN_STATUS).build());
    }

    @Override
    public void changeMobileFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void addJobIdSuccess(String msg) {

    }

    @Override
    public void addJobIdFailed(String errMsg) {

    }

}
