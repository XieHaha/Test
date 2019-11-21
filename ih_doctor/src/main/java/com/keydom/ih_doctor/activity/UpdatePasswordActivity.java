package com.keydom.ih_doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.ClearEditText;
import com.keydom.ih_common.view.MButton;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.controller.UpdatePasswordController;
import com.keydom.ih_doctor.activity.view.UpdatePasswordView;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：修改密码
 * @Author：song
 * @Date：18/11/2 下午5:30
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:30
 */
public class UpdatePasswordActivity extends BaseControllerActivity<UpdatePasswordController> implements UpdatePasswordView {
    /**
     * 判断是否需要输入电话号码的key<br/>
     * true  登录前修改密码，不存在手机号码，需要手动输入手机号码<br/>
     * false 登录后修改密码，存在手机号码，不需要手动输入手机号码<br/>
     */
    public static final String INPUT_PHONE_NUMBER = "input_phone_number";
    private MButton getIdentifyingCodeBt;
    private Button nextStepBt;
    private RadioButton agreementRb;
    private ClearEditText phoneInputEt;
    private EditText codeInputEt;
    private TextView registerAgreementTv;
    /**
     * true  登录前修改密码，不存在手机号码，需要手动输入手机号码<br/>
     * false 登录后修改密码，存在手机号码，不需要手动输入手机号码<br/>
     */
    private boolean isInputPassword;

    /**
     * 登录前修改密码，需要输入手机号
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, UpdatePasswordActivity.class);
        context.startActivity(starter);
    }


    /**
     * 登录后修改密码，不需要输入手机号
     *
     * @param context
     */
    public static void startWithPhoneNumber(Context context) {
        Intent starter = new Intent(context, UpdatePasswordActivity.class);
        starter.putExtra(INPUT_PHONE_NUMBER, false);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_updatepassword_layout;
    }

    @Override
    public String getPhoneNo() {
        return phoneInputEt.getText().toString();
    }

    @Override
    public String getIdentifyingCode() {
        return codeInputEt.getText().toString();
    }

    @Override
    public boolean getIsAgreement() {
        return agreementRb.isChecked();
    }

    @Override
    public void getIdentifyingCodeSuccess() {
        getIdentifyingCodeBt.startTimer();
    }

    @Override
    public void getIdentifyingCodeFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void verifyCodeSuccess() {
        SetPasswordActivity.start(UpdatePasswordActivity.this, getPhoneNo());

    }

    @Override
    public void verifyCodeFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("修改密码");
        isInputPassword = getIntent().getBooleanExtra(INPUT_PHONE_NUMBER, true);
        getIdentifyingCodeBt = (MButton) this.findViewById(R.id.get_identifying_code_bt);
        nextStepBt = (Button) this.findViewById(R.id.next_step);
        agreementRb = (RadioButton) this.findViewById(R.id.agreement_rb);
        phoneInputEt = (ClearEditText) this.findViewById(R.id.phone_input_et);
        codeInputEt = (EditText) this.findViewById(R.id.code_input_et);
        registerAgreementTv = (TextView) this.findViewById(R.id.register_agreement_tv);
        getIdentifyingCodeBt.setOnClickListener(getController());
        nextStepBt.setOnClickListener(getController());
        registerAgreementTv.setOnClickListener(getController());
        if (!isInputPassword) {
            phoneInputEt.setText(SharePreferenceManager.getPhoneNumber());
            phoneInputEt.setTextColor(getResources().getColor(R.color.fontColorDirection));
            phoneInputEt.setEnabled(false);
        }
    }
}
