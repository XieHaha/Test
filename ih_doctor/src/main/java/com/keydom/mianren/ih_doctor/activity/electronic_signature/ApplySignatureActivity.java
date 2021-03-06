package com.keydom.mianren.ih_doctor.activity.electronic_signature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.MButton;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.controller.ApplySignatureController;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.ApplySignatureView;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.SignRegInfoBean;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
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
public class ApplySignatureActivity extends BaseControllerActivity<ApplySignatureController> implements ApplySignatureView {


    private EditText phone_input_et, code_input_et, user_name_input_et, card_input_et;
    private MButton get_identifying_code_bt;

    /**
     * 启动电子签名设置页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, ApplySignatureActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_apply_sign_layout;
    }

    private void initView() {
        phone_input_et = this.findViewById(R.id.phone_input_et);
        code_input_et = this.findViewById(R.id.code_input_et);
        user_name_input_et = this.findViewById(R.id.user_name_input_et);
        card_input_et = this.findViewById(R.id.card_input_et);
        get_identifying_code_bt = this.findViewById(R.id.get_identifying_code_bt);
        get_identifying_code_bt.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                String phoneNum = phone_input_et.getText().toString().trim();
                if (PhoneUtils.isMobileEnable(phoneNum)) {
                    getController().sendCode(phoneNum);
                } else {
                    ToastUtil.showMessage(getContext(), "请输入正确的手机号");
                }

            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子签名");
        setRightTxt("提交");
        initView();
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (checkMap()) {
                    getController().applySign();
                }
            }
        });
    }

    private boolean checkMap() {
        if (user_name_input_et.getText().toString().trim() == null || "".equals(user_name_input_et.getText().toString().trim()))
            return false;
        if (card_input_et.getText().toString().trim() == null || "".equals(user_name_input_et.getText().toString().trim()))
            return false;
        if (phone_input_et.getText().toString().trim() == null || "".equals(user_name_input_et.getText().toString().trim()))
            return false;
        if (code_input_et.getText().toString().trim() == null || "".equals(user_name_input_et.getText().toString().trim()))
            return false;
        return true;
    }

    @Override
    public Map<String, Object> getApplyMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", user_name_input_et.getText().toString().trim());
        map.put("idNum", card_input_et.getText().toString().trim());
        map.put("mobile", phone_input_et.getText().toString().trim());
        map.put("verificationCode", code_input_et.getText().toString().trim());
        map.put("userId", MyApplication.userInfo.getId());
        return map;
    }

    @Override
    public void applySuccess(String result) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
        SignRegInfoBean regInfoBean = JSON.parseObject(result, new TypeReference<SignRegInfoBean>() {
        });
        MyApplication.userInfo.setMsspId(regInfoBean.getMsspId());
        SignatureActivity.start(ApplySignatureActivity.this);
        finish();
    }

    @Override
    public void applyFailed(String errMsg) {
        ToastUtil.showMessage(ApplySignatureActivity.this, errMsg);
    }

    @Override
    public void getIdentifyingCodeSuccess() {
        get_identifying_code_bt.startTimer();
    }

    @Override
    public void getIdentifyingCodeFailed(String errMsg) {
        ToastUtil.showMessage(ApplySignatureActivity.this, errMsg);
    }

    @Override
    public void getUserSuccess(SignRegInfoBean data) {

    }

    @Override
    public void getUserFailed(String errMsg) {

    }

    @Override
    public void changeMobileSuccess(String msg) {

    }

    @Override
    public void changeMobileFailed(String errMsg) {

    }

    @Override
    public void addJobIdSuccess(String msg) {

    }

    @Override
    public void addJobIdFailed(String errMsg) {

    }
}
